package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.TinyRadarException;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.core.util.DummyObjects;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class SvgMarkerRotationTest {

    @Test
    @Disabled("Manual activation only")
    void demo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        TinyRadar.pos = new WGS84(51.47002f, -0.45429f);
        FlatMacDarkLaf.setup();
        Markers.load();

        var a1 = createAircraftWithCat("A1");
        var a2 = createAircraftWithCat("A2");
        var a3 = createAircraftWithCat("A3");
        var a5 = createAircraftWithCat("A5");
        var a6 = createAircraftWithCat("A6");
        var a7 = createAircraftWithCat("A7");
        var b6 = createAircraftWithCat("B6");
        var md11 = createAircraftWithType("MD11");
        var b52 = createAircraftWithType("B52");

        a1.setLon(a1.getLon() + 0.01);
        a2.setLon(a1.getLon() + 0.03);
        a3.setLon(a1.getLon() + 0.05);
        a5.setLon(a1.getLon() + 0.07);
        a6.setLon(a1.getLon() + 0.09);
        a7.setLon(a1.getLon() + 0.11);
        b6.setLon(a1.getLon() + 0.13);
        md11.setLon(a1.getLon() + 0.15);
        b52.setLon(a1.getLon() + 0.17);

        var list = List.of(a1, a2, a3, a5, a6, a7, b6, md11, b52);

        SwingUtilities.invokeLater(() -> {
            var frame = Gui.frame("RotationTest", new Dimension(350, 350));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    latch.countDown();
                }
            });

            LayerAirborne layer = new LayerAirborne();
            frame.add(layer);

            CompletableFuture.runAsync(() -> {
                for (int i = 0; i <= 360; i++) {
                    a1.setTrack((double) i);
                    a2.setTrack((double) i);
                    a3.setTrack((double) i);
                    a5.setTrack((double) i);
                    a6.setTrack((double) i);
                    a7.setTrack((double) i);
                    b6.setTrack((double) i);
                    md11.setTrack((double) i);
                    b52.setTrack((double) i);
                    layer.onFlightsUpdateEvent(new FlightsUpdateEvent(list));

                    System.out.println(i + "°");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new TinyRadarException(e);
                    }
                }
            });

        });

        latch.await();
    }

    private Aircraft createAircraftWithCat(String category) {
        var aircraft = DummyObjects.getList().getFirst().clone();
        aircraft.setCategory(category);
        aircraft.setT("test");
        aircraft.setFlight(UUID.randomUUID().toString());

        return aircraft;
    }

    private Aircraft createAircraftWithType(String type) {
        var aircraft = DummyObjects.getList().getFirst().clone();
        aircraft.setT(type);
        aircraft.setFlight(UUID.randomUUID().toString());

        return aircraft;
    }

}
