package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.parser.SVGLoader;
import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import dev.mf1.tinyradar.core.event.ZoomChangeEvent;
import dev.mf1.tinyradar.gui.map.MapUtils;
import dev.mf1.tinyradar.gui.svg.SvgRotatablePanel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
final class LayerAirborne extends TransparentPanel {

    private final List<Aircraft> flights = new CopyOnWriteArrayList<>();
    private final List<SvgRotatablePanel<Aircraft>> markers = new ArrayList<>();
    private Aircraft selectedAircraft;

    LayerAirborne() {
        TinyRadar.BUS.register(this);
    }

    LayerAirborne(Dimension dimension) {
        setSize(dimension);
        setLayout(null);

        TinyRadar.BUS.register(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAircraftSelectionEvent(AircraftSelectionEvent event) {
        selectedAircraft = event.aircraft();
        SwingUtilities.invokeLater(this::repaint);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onFlightsUpdateEvent(FlightsUpdateEvent event) {
        flights.clear();
        flights.addAll(event.flights());

        refresh(event.flights());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onZoomChangeEvent(ZoomChangeEvent event) {
        refresh();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onLocationChangeEvent(LocationChangeEvent event) {
        refresh();
    }

    public void refresh() {
        refresh(flights);
    }

    public void refresh(List<Aircraft> flights) {
        SwingUtilities.invokeLater(() -> {
            if (getComponents().length > 0) removeAll();

            Set<Aircraft> aircraftSet = new HashSet<>(flights);
            markers.removeIf(svg -> !aircraftSet.contains(svg.getT()));

            int w = getWidth();
            int h = getHeight();

            for (Aircraft a : flights) {
                var pos = new WGS84(a.getLat().floatValue(), a.getLon().floatValue());
                var proj = MapUtils.getScreenPosition(pos, TinyRadar.zoom, TinyRadar.pos, w, h);

                var doc = Markers.get(a);
                var x = proj[0] - (int) doc.size().getWidth() / 2;
                var y = proj[1] - (int) doc.size().getHeight() / 2;

                var marker = markers.stream().filter(p -> p.getT().equals(a)).findFirst().orElseGet(() -> {
                    var newMarker = new SvgRotatablePanel<>(a, doc, new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            TinyRadar.BUS.post(new AircraftSelectionEvent(a));
                        }
                    });
                    markers.add(newMarker);
                    return newMarker;
                });

                marker.setBounds(x, y, (int) doc.size().getWidth(), (int) doc.size().getHeight());
                marker.setDegrees(a.getAnyHeading());
                marker.setColor(a.isOnGround() ? Color.GRAY : Color.WHITE);
                add(marker);

//                int pX = x + (int) doc.size().getWidth() + 1;
//                int pY = y + (int) doc.size().getHeight() + 1;
//                var pF = new FlightDisplayPanel(a);
//                pF.setBounds(pX, pY, 64, 40);
//
//                add(pF);
            }

            repaint();
        });
    }

}
