package dev.mf1.tinyradar.gui.svg;

import com.github.weisj.jsvg.SVGDocument;
import dev.mf1.tinyradar.gui.Gui;
import dev.mf1.tinyradar.gui.Markers;
import dev.mf1.tinyradar.gui.TransparentPanel;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SvgPanel<T> extends TransparentPanel {

    protected final SVGDocument DOCUMENT;
    protected CustomColorsProcessor colorsProcessor;

    @Getter
    private final T t;

    public SvgPanel(T t, SVGDocument document) {
        this.t = t;
        DOCUMENT = document;
        colorsProcessor = Markers.COLORS_PROCESSOR;
    }

    public void setColor(Color color) {
        DynamicAWTSvgPaint paint = colorsProcessor.customColorForId("icon");
        if (paint != null) {
            paint.setColor(color);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Gui.applyQualityRenderingHints(g2d);

        DOCUMENT.render(this, g2d);
        g2d.dispose();
    }


}
