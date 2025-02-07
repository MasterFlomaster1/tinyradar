package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Slf4j
public final class Gui implements ShutdownListener {

    public static SimpleFrame rootFrame;
    private static final List<ShutdownListener> shutdownListeners = new ArrayList<>();
    private final Preferences preferences = Preferences.userNodeForPackage(Gui.class);

    private Gui() {
    }

    public static void main(String[] args) {
        new Gui().start();
    }

    private void start() {
        log.info("Starting TinyRadar");

        addShutdownListener(this);
        Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler());
        FlatMacDarkLaf.setup();
        Markers.load();

        var lat = preferences.getFloat("lat", 40.6397f);
        var lon = preferences.getFloat("lon", -73.7788f);
        var range = preferences.getInt("range", 10);
        var zoom = preferences.getInt("zoom", 11);

        TinyRadar.pos = new WGS84(lat, lon);
        TinyRadar.zoom = zoom;
        TinyRadar.range = range;
        TinyRadar.of().setup();
        TinyRadar.of().start();

        SwingUtilities.invokeLater(() -> {
            rootFrame = new SimpleFrame();
            rootFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    shutdownListeners.forEach(ShutdownListener::onShutdown);
                }
            });
            log.info(systemInfo());

            TinyRadar.BUS.post(new LocationChangeEvent(TinyRadar.pos));
        });
    }

    public static void addShutdownListener(ShutdownListener listener) {
        shutdownListeners.add(listener);
    }

    private static String systemInfo() {
        String javaVendor = System.getProperty( "java.vendor" );
        if( "Oracle Corporation".equals( javaVendor ) )
            javaVendor = null;
        double systemScaleFactor = UIScale.getSystemScaleFactor(rootFrame.getGraphicsConfiguration());
        float userScaleFactor = UIScale.getUserScaleFactor();
        Font font = UIManager.getFont( "Label.font" );

        return "(Java " + System.getProperty( "java.version" )
                + (javaVendor != null ? ("; " + javaVendor) : "")
                + (systemScaleFactor != 1 ? (";  system scale factor " + systemScaleFactor) : "")
                + (userScaleFactor != 1 ? (";  user scale factor " + userScaleFactor) : "")
                + (systemScaleFactor == 1 && userScaleFactor == 1 ? "; no scaling" : "")
                + "; " + font.getFamily() + " " + font.getSize()
                + (font.isBold() ? " BOLD" : "")
                + (font.isItalic() ? " ITALIC" : "")
                + ")";
    }

    @Override
    public void onShutdown() {
        preferences.putFloat("lat", TinyRadar.pos.latitude());
        preferences.putFloat("lon", TinyRadar.pos.longitude());
        preferences.putInt("range", TinyRadar.range);
        preferences.putInt("zoom", TinyRadar.zoom);
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

}
