package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import lombok.Getter;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SvgPanel<T> extends TransparentPanel {

    protected final SVGDocument DOCUMENT;

    @Getter
    private final T t;

    public SvgPanel(T t, SVGDocument document) {
        this.t = t;
        DOCUMENT = document;
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
