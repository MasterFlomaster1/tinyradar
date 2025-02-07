package dev.mf1.tinyradar.gui;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

final class UtcTimePanel extends TransparentPanel {

    private final JLabel utcTimeLabel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.from(ZoneOffset.UTC));

    private Instant utcTime = Instant.now();

    UtcTimePanel() {
        utcTimeLabel = new JLabel();
        utcTimeLabel.setForeground(Color.decode("#F7F7F7"));
        utcTimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(utcTimeLabel);

        new Timer(1000, e -> {
            utcTime = Instant.now();

            String value = formatter.format(utcTime) + " UTC";
            utcTimeLabel.setText(value);

            repaint();
        }).start();
    }

}
