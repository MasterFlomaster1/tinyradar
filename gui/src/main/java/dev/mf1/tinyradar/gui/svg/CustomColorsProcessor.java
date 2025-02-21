package dev.mf1.tinyradar.gui.svg;

import com.github.weisj.jsvg.attributes.paint.SimplePaintSVGPaint;
import com.github.weisj.jsvg.parser.AttributeNode;
import com.github.weisj.jsvg.parser.DomProcessor;
import com.github.weisj.jsvg.parser.ParsedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomColorsProcessor implements DomProcessor {

    private final Map<String, DynamicAWTSvgPaint> customColors = new HashMap<>();

    public CustomColorsProcessor(@NotNull List<String> elementIds) {
        for (String elementId : elementIds) {
            customColors.put(elementId, new DynamicAWTSvgPaint(Color.BLACK));
        }
    }

    @Nullable
    DynamicAWTSvgPaint customColorForId(@NotNull String id) {
        return customColors.get(id);
    }

    @Override
    public void process(ParsedElement root) {
        processImpl(root);
        root.children().forEach(this::process);
    }

    private void processImpl(ParsedElement element) {
        // Obtain the id of the element
        // Note: There that Element also has a node() method to obtain the SVGNode. However during the pre-processing
        // phase the SVGNode is not yet fully parsed and doesn't contain any non-defaulted information.
        String nodeId = element.id();

        // Check if this element is one of the elements we want to change the color of
        if (customColors.containsKey(nodeId)) {
            // The attribute node contains all the attributes of the element specified in the markup
            // Even those which aren't valid for the element
            AttributeNode attributeNode = element.attributeNode();
            DynamicAWTSvgPaint dynamicColor = customColors.get(nodeId);

            // This assumed that the fill attribute is a color and not a gradient or pattern.
            Color color = attributeNode.getColor("fill");

            dynamicColor.setColor(color);

            // This can be anything as long as it's unique
            String uniqueIdForDynamicColor = UUID.randomUUID().toString();

            // Register the dynamic color as a custom element
            element.registerNamedElement(uniqueIdForDynamicColor, dynamicColor);

            // Refer to the custom element as the fill attribute
            attributeNode.attributes().put("fill", uniqueIdForDynamicColor);

            // Note: This class can easily be adapted to also support changing the stroke color.
            // With a bit more work it could also support changing the color of gradients and patterns.
        }
    }
}

class DynamicAWTSvgPaint implements SimplePaintSVGPaint {

    private @NotNull Color color;

    DynamicAWTSvgPaint(@NotNull Color color) {
        this.color = color;
    }

    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    public @NotNull Color color() {
        return color;
    }

    @Override
    public @NotNull Paint paint() {
        return color;
    }
}
