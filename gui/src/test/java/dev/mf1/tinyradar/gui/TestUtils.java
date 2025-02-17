package dev.mf1.tinyradar.gui;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

    private static final List<Color> brightColors = List.of(
            Color.decode("#FF5733"),
            Color.decode("#33FF57"),
            Color.decode("#5733FF"),
            Color.decode("#FF33A1"),
            Color.decode("#33D4FF"),
            Color.decode("#FFD433"),
            Color.decode("#FF3333"),
            Color.decode("#33FFCE"),
            Color.decode("#A833FF"),
            Color.decode("#33FF7C"),
            Color.decode("#FF5733"),
            Color.decode("#338AFF"),
            Color.decode("#FFC433"),
            Color.decode("#FF3399"),
            Color.decode("#33FFB5"),
            Color.decode("#FF9933"),
            Color.decode("#33E6FF"),
            Color.decode("#FF33FF"),
            Color.decode("#75FF33"),
            Color.decode("#FF3366")
    );

    public static Color randomColor() {
        return brightColors.get(ThreadLocalRandom.current().nextInt(brightColors.size()));
    }

    public static void applyColorBorder(JComponent component) {
        component.setBorder(new LineBorder(randomColor()));
    }

}
