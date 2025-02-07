package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.gui.map.MapPanel;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class LayeredDisplayPane extends JLayeredPane {

    public LayeredDisplayPane() {
        var rootLayer = new JPanel();
        var dimension = new Dimension(1280, 720);
        rootLayer.setSize(dimension);
        rootLayer.setBackground(Color.BLACK);

        var layerMap = new MapPanel();
        layerMap.setSize(dimension);

        LayerConfig layerConfig = new LayerConfig();
        LayerSurface layerSurface = new LayerSurface();
        LayerAirborne layerAirborne = new LayerAirborne();

        add(rootLayer, Integer.valueOf(1));
        add(layerMap, Integer.valueOf(2));
        add(layerConfig, Integer.valueOf(3));
        add(layerSurface, Integer.valueOf(4));
        add(layerAirborne, Integer.valueOf(5));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                Component c = (Component) e.getSource();
                int width = c.getWidth();
                int height = c.getHeight();

                rootLayer.setSize(width, height);
                layerMap.setSize(width, height);
                layerAirborne.setSize(width, height);
                layerSurface.setSize(width, height);
                layerConfig.setSize(width, height);
            }
        });
    }

}
