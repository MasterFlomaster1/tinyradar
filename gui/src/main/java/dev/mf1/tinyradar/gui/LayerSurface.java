package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;
import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.AirportsUpdateEvent;
import dev.mf1.tinyradar.core.oa.Airport;
import dev.mf1.tinyradar.gui.map.MapUtils;
import dev.mf1.tinyradar.gui.svg.SvgPanel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class LayerSurface extends TransparentPanel {

    private List<Airport> airports = new ArrayList<>();

    private static final SVGDocument doc = new SVGLoader().load(Resources.getAsStream("/airport_icon.svg"));
    private final List<SvgPanel<Airport>> markers = new ArrayList<>();

    public LayerSurface(Dimension dimension) {
        setSize(dimension);
        setLayout(null);

        TinyRadar.BUS.register(this);
    }

    public void refresh() {
        refresh(airports);
    }

    public void refresh(List<Airport> airports) {
        SwingUtilities.invokeLater(() -> {
            if (getComponents().length > 0) removeAll();
            if (TinyRadar.zoom < 8) return;

            Set<Airport> airportSet = new HashSet<>(airports);
            markers.removeIf(svg -> !airportSet.contains(svg.getT()));

            int w = getWidth();
            int h = getHeight();

            for (Airport a : airports) {
                var pos = new WGS84((float) a.getLatitude(), (float) a.getLongitude());
                var proj = MapUtils.getScreenPosition(pos, TinyRadar.zoom, TinyRadar.pos, w, h);
                int x = proj[0] - 8;
                int y = proj[1] - 8;

                var marker = markers.stream().filter(p -> p.getT().equals(a)).findFirst().orElseGet(() -> {
                    var newMarker = new SvgPanel<>(a, doc);
                    newMarker.setToolTipText("%s %n %s".formatted(a.getName(), a.getIdent()));
                    markers.add(newMarker);
                    return newMarker;
                });

                marker.setBounds(x, y, 16, 16);
                add(marker);
            }

            repaint();
        });
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAirportsUpdateEvent(AirportsUpdateEvent event) {
        this.airports = event.airports();
        refresh(event.airports());
    }

}
