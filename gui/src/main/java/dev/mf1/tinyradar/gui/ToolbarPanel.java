package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dev.mf1.tinyradar.core.Filter;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.event.ZoomChangeEvent;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

@Slf4j
final class ToolbarPanel extends JPanel {

    private JFrame presetsFrame;

    ToolbarPanel() {
        setLayout(new MigLayout("insets 0", "[grow]", "[]"));

        var zoomOutButton = new JButton("");
        zoomOutButton.setToolTipText("Zoom out");
        zoomOutButton.setIcon(new FlatSVGIcon(Resources.get("/icons/zoom-out.svg")));
        zoomOutButton.addActionListener(e -> {
            if (TinyRadar.zoom == 3)
                return;

            TinyRadar.zoom--;
            TinyRadar.BUS.post(new ZoomChangeEvent());
            TinyRadar.of().restart();
        });

        var zoomInButton = new JButton("");
        zoomInButton.setToolTipText("Zoom in");
        zoomInButton.setIcon(new FlatSVGIcon(Resources.get("/icons/zoom-in.svg")));
        zoomInButton.addActionListener(e -> {
            if (TinyRadar.zoom == 16)
                return;

            TinyRadar.zoom++;
            TinyRadar.BUS.post(new ZoomChangeEvent());
            TinyRadar.of().restart();
        });

        var settingsButton = new JButton("");
        settingsButton.setToolTipText("Settings");
        settingsButton.setIcon(new FlatSVGIcon(Resources.get("/icons/settings.svg")));

        var geoButton = new JButton("");
        geoButton.setToolTipText("Select map position from presets");
        geoButton.setIcon(new FlatSVGIcon(Resources.get("/icons/globe.svg")));
        geoButton.addActionListener(e -> {
            if (presetsFrame == null) {
                SwingUtilities.invokeLater(() -> {
                    presetsFrame = new PresetsFrame();
                    presetsFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            presetsFrame = null;
                        }
                    });
                });
            } else {
                presetsFrame.toFront();
            }
        });

        var milButton = new JButton("");
        milButton.setToolTipText("Show only military aircraft");
        milButton.setIcon(new FlatSVGIcon(Resources.get("/icons/radio.svg")));
        milButton.addActionListener(e -> {
            Filter.showMilOnly = !Filter.showMilOnly;
            TinyRadar.of().restart();
        });

        var githubButton = new JButton("");
        githubButton.setToolTipText("Open project on GitHub");
        githubButton.setIcon(new FlatSVGIcon(Resources.get("/icons/github.svg")));
        githubButton.addActionListener(e -> {
            if (!Desktop.isDesktopSupported()) {
                log.error("Desktop is not supported");
                return;
            }

            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                log.error("Desktop browse() is not supported");
                return;
            }

            try {
                Desktop.getDesktop().browse(new URI("https://github.com/MasterFlomaster1/tinyradar"));
            } catch (Exception ex) {
                log.error("Error opening link ", ex);
            }
        });

        var toolbar = new JToolBar();
        toolbar.setMargin(new Insets(0, 0, 0, 0));
        toolbar.add(zoomOutButton);
        toolbar.add(zoomInButton);
        toolbar.addSeparator();
        toolbar.add(settingsButton);
        toolbar.add(geoButton);
        toolbar.add(milButton);
        toolbar.add(githubButton);

        add(toolbar);
    }

}
