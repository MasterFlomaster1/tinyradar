package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.mf1.tinyradar.core.util.DummyObjects;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

class FlightDisplayPanelTest {

    @Test
    @Disabled("Manual activation required")
    void demo() throws InterruptedException {
        FlatMacDarkLaf.setup();

        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            var frame = Gui.frame("FlightDisplayPanelTest", new Dimension(100, 100));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    latch.countDown();
                }
            });

            var aircraft = DummyObjects.getList().get(1);
            var panel = new FlightDisplayPanel(aircraft);
            TestUtils.applyColorBorder(panel);
            frame.add(panel);
        });

        latch.await();
    }

}