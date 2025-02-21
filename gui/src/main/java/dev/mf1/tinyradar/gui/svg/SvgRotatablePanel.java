package dev.mf1.tinyradar.gui.svg;

import com.github.weisj.jsvg.SVGDocument;
import dev.mf1.tinyradar.gui.Gui;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.UUID;

@Setter
public class SvgRotatablePanel<T> extends SvgPanel<T> {

    private double degrees;
    private String uuid = UUID.randomUUID().toString();

    public SvgRotatablePanel(T t, SVGDocument document) {
        super(t, document);
    }

    public SvgRotatablePanel(T t, SVGDocument document, MouseListener listener) {
        super(t, document);

        addMouseListener(listener);
    }

    @Override
    public void setColor(Color color) {
        DynamicAWTSvgPaint paint = colorsProcessor.customColorForId(uuid);
        if (paint != null) {
            paint.setColor(color);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Gui.applyQualityRenderingHints(g2d);

        AffineTransform oldTransform = g2d.getTransform();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        g2d.rotate(Math.toRadians(degrees), cx, cy);

        DOCUMENT.render(this, g2d);

        g2d.setTransform(oldTransform);
        g2d.dispose();
    }

}
