package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.al.Aircraft;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public final class FlightDisplayPanel extends JPanel {

    Aircraft aircraft;

    FlightDisplayPanel(Aircraft aircraft) {
        this.aircraft = aircraft;

        this.setLayout(new MigLayout("fill, insets 0, gap 0", "[]", "[]"));
//            this.setBorder(new LineBorder(Color.YELLOW));

        this.add(new JLabel(aircraft.getFlight()), "wrap");
        this.add(new JLabel(aircraft.getAltBaro() + " " + aircraft.getAnySpeed()), "wrap");
        this.add(new JLabel(aircraft.getT()));
    }

}
