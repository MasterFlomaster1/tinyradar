package dev.mf1.tinyradar.gui.map;

import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import dev.mf1.tinyradar.core.event.ZoomChangeEvent;
import dev.mf1.tinyradar.core.util.HttpFileDownloader;
import dev.mf1.tinyradar.gui.Gui;
import dev.mf1.tinyradar.gui.TransparentPanel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class MapPanel extends TransparentPanel {

    private static final int TILE_SIZE = 256;
    private final List<Tile> tileList = new CopyOnWriteArrayList<>();
    private int centerTileX;
    private int centerTileY;

    public MapPanel() {
        setSize(new Dimension(1280, 720));

        updateMapRadius();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                updateMapRadius();

                var n = loadTiles(TinyRadar.pos, getWidth(), getHeight());
                if (hasTileListChanged(n)) {
                    tileList.clear();
                    tileList.addAll(n);
                    repaint();
                }
            }
        });

        TinyRadar.BUS.register(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;
        Gui.applyQualityRenderingHints(g2d);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        var p = MapUtils.getPointInTile(TinyRadar.pos, TinyRadar.zoom);

        tileList.stream()
                .parallel()
                .forEach(t -> {
                    Point tileCoords = new Point(t.getX(), t.getY());
                    BufferedImage tileImage = t.getImage();

                    int dx = (tileCoords.x - centerTileX) * TILE_SIZE - p[0];
                    int dy = (tileCoords.y - centerTileY) * TILE_SIZE - p[1];

                    int drawX = centerX + dx;
                    int drawY = centerY + dy;

                    g2d.drawImage(tileImage, drawX, drawY, TILE_SIZE, TILE_SIZE, null);
                });
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onLocationChangeEvent(LocationChangeEvent event) {
        var tiles = loadTiles(event.newLocation(), getWidth(), getHeight());
        tileList.clear();
        tileList.addAll(tiles);

        var c = MapUtils.getCenterTile(tiles);
        centerTileX = c.x;
        centerTileY = c.y;

        repaint();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onZoomChangeEvent(ZoomChangeEvent event) {
        updateMapRadius();
        updateTiles();

        TinyRadar.of().updateAirportsInRange();
        repaint();
    }

    private boolean hasTileListChanged(List<Tile> newTiles) {
        if (newTiles.size() != tileList.size()) {
            return true;
        }

        Set<Tile> currentTileSet = new HashSet<>(tileList);
        return !currentTileSet.containsAll(newTiles);
    }

    private void updateMapRadius() {
        var r = MapUtils.calculateMapRadius(TinyRadar.pos.latitude(), TinyRadar.zoom, getWidth(), getHeight());
        TinyRadar.range = (int) Math.round(r);
    }

    private void updateTiles() {
        var n = loadTiles(TinyRadar.pos, getWidth(), getHeight());
        tileList.clear();
        tileList.addAll(n);

        var c = MapUtils.getCenterTile(n);
        centerTileX = c.x;
        centerTileY = c.y;
    }

    private List<Tile> loadTiles(WGS84 location, int width, int height) {
        var tiles = MapUtils.fetchTiles(location, width, height, TinyRadar.zoom);

        for (var tile : tiles) {
            var target = Paths.get(TinyRadar.of().getTilesDir().toString(), tile.toFilename());

            if (Files.exists(target)) {
                try {
                    tile.setImage(ImageIO.read(target.toFile()));
                } catch (IOException e) {
                    log.error("Error reading tile image from file: {}", target, e);
                }
                continue;
            }

            HttpFileDownloader.asyncDownload(tile.toUrl(), target)
                    .thenAcceptAsync(downloadedFile -> {
                        try {
                            tile.setImage(ImageIO.read(downloadedFile.body().toFile()));
                            repaint();
                        } catch (IOException e) {
                            log.error("Error setting image for tile from downloaded file: {}", downloadedFile, e);
                        }
                    })
                    .exceptionally(e -> {
                        log.error("Error downloading tile: {}", tile.toUrl(), e);
                        return null;
                    });
        }

        return tiles;
    }

}
