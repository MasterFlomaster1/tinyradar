package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.DefaultParserProvider;
import com.github.weisj.jsvg.parser.DomProcessor;
import com.github.weisj.jsvg.parser.LoaderContext;
import com.github.weisj.jsvg.parser.SVGLoader;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.gui.svg.CustomColorsProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adopted from <a href="https://github.com/wiedehopf/tar1090/blob/master/html/markers.js"></a>
 */
public final class Markers {

    private static SVGDocument airliner;
    private static SVGDocument cessna;
    private static SVGDocument jetSwept;
    private static SVGDocument hiPerf;
    private static SVGDocument heavy2E;
    private static SVGDocument helicopter;
    private static SVGDocument uav;
    private static SVGDocument groundTower;

    private final static Map<String, SVGDocument> types = new HashMap<>();
    private static Map<String, SVGDocument> category;

    public static CustomColorsProcessor COLORS_PROCESSOR = new CustomColorsProcessor(List.of("icon"));
    public static final SVGLoader SVG_LOADER = new SVGLoader();
    public static LoaderContext LOADER_CONTEXT;

    private Markers() {
    }

    public static void load() {

        LOADER_CONTEXT = LoaderContext.builder()
                .parserProvider(new DefaultParserProvider() {
                    @Override
                    public DomProcessor createPreProcessor() {
                        return COLORS_PROCESSOR;
                    }
                })
                .build();

        var context = LoaderContext.builder()
                .parserProvider(new DefaultParserProvider() {
                    @Override
                    public DomProcessor createPreProcessor() {
                        return COLORS_PROCESSOR;
                    }
                })
                .build();

        airliner = SVG_LOADER.load(Resources.getAsStream("/markers/airliner.svg"), null, context);
        cessna = SVG_LOADER.load(Resources.getAsStream("/markers/cessna.svg"), null, context);
        heavy2E = SVG_LOADER.load(Resources.getAsStream("/markers/heavy2e.svg"), null, context);
        SVGDocument md11 = SVG_LOADER.load(Resources.getAsStream("/markers/md11.svg"), null, context);
        SVGDocument c130 = SVG_LOADER.load(Resources.getAsStream("/markers/c130.svg"), null, context);
        hiPerf = SVG_LOADER.load(Resources.getAsStream("/markers/hi_perf.svg"), null, context);
        SVGDocument f18 = SVG_LOADER.load(Resources.getAsStream("/markers/f18.svg"), null, context);
        SVGDocument e3awacs = SVG_LOADER.load(Resources.getAsStream("/markers/e3awacs.svg"), null, context);
        SVGDocument heavy4E = SVG_LOADER.load(Resources.getAsStream("/markers/heavy4e.svg"), null, context);
        jetSwept = SVG_LOADER.load(Resources.getAsStream("/markers/jet_swept.svg"), null, context);
        SVGDocument twinLarge = SVG_LOADER.load(Resources.getAsStream("/markers/twin_large.svg"), null, context);
        SVGDocument v22Fast = SVG_LOADER.load(Resources.getAsStream("/markers/v22_fast.svg"), null, context);
        SVGDocument t38 = SVG_LOADER.load(Resources.getAsStream("/markers/t38.svg"), null, context);
        uav = SVG_LOADER.load(Resources.getAsStream("/markers/uav.svg"), null, context);
        SVGDocument typhoon = SVG_LOADER.load(Resources.getAsStream("/markers/typhoon.svg"), null, context);
        SVGDocument c17 = SVG_LOADER.load(Resources.getAsStream("/markers/c17.svg"), null, context);
        SVGDocument c5 = SVG_LOADER.load(Resources.getAsStream("/markers/c5.svg"), null, context);
        SVGDocument a380 = SVG_LOADER.load(Resources.getAsStream("/markers/a380.svg"), null, context);
        helicopter = SVG_LOADER.load(Resources.getAsStream("/markers/helicopter.svg"), null, context);
        SVGDocument a10 = SVG_LOADER.load(Resources.getAsStream("/markers/a10.svg"), null, context);
        SVGDocument apache = SVG_LOADER.load(Resources.getAsStream("/markers/apache.svg"), null, context);
        SVGDocument blackhawk = SVG_LOADER.load(Resources.getAsStream("/markers/blackhawk.svg"), null, context);
        SVGDocument b52 = SVG_LOADER.load(Resources.getAsStream("/markers/b52.svg"), null, context);
        SVGDocument b707 = SVG_LOADER.load(Resources.getAsStream("/markers/b707.svg"), null, context);
        SVGDocument groundSquare = SVG_LOADER.load(Resources.getAsStream("/markers/ground_square.svg"), null, context);
        groundTower = SVG_LOADER.load(Resources.getAsStream("/markers/ground_tower.svg"), null, context);
        SVGDocument puma = SVG_LOADER.load(Resources.getAsStream("/markers/puma.svg"), null, context);
        SVGDocument f15 = SVG_LOADER.load(Resources.getAsStream("/markers/md_f15.svg"), null, context);
        SVGDocument b1bLancer = SVG_LOADER.load(Resources.getAsStream("/markers/b1b_lancer.svg"), null, context);

        category = Map.of(
                "A1", cessna,
                "A2", jetSwept,
                "A3", airliner,
                "A4", airliner,
                "A5", heavy2E,
                "A6", hiPerf,
                "A7", helicopter,
                "B4", cessna,
                "B6", uav,
                "C3", groundTower
        );


        types.put("A318", airliner);
        types.put("A319", airliner);
        types.put("A19N", airliner);
        types.put("A320", airliner);
        types.put("A20N", airliner);
        types.put("A321", airliner);
        types.put("A21N", airliner);

        types.put("A306", heavy2E);
        types.put("A330", heavy2E);
        types.put("A332", heavy2E);
        types.put("A333", heavy2E);
        types.put("A338", heavy2E);
        types.put("A339", heavy2E);
        types.put("DC10", md11);
        types.put("MD11", md11);

        types.put("A359", heavy2E);
        types.put("A35K", heavy2E);

        types.put("A388", a380);

        types.put("B731", airliner);
        types.put("B732", airliner);
        types.put("B733", airliner);
        types.put("B734", airliner);
        types.put("B735", airliner);
        types.put("B736", airliner);
        types.put("B737", airliner);
        types.put("B738", airliner);
        types.put("B739", airliner);
        types.put("B37M", airliner);
        types.put("B38M", airliner);
        types.put("B39M", airliner);

        types.put("P8", airliner);
        types.put("P8 ?", airliner);

        types.put("J328", airliner);
        types.put("E170", airliner);
        types.put("E75S/L", airliner);
        types.put("E75L", airliner);
        types.put("E75S", airliner);
        types.put("A148", airliner);
        types.put("RJ70", airliner);
        types.put("RJ85", airliner);
        types.put("RJ1H", airliner);
        types.put("B461", airliner);
        types.put("B462", airliner);
        types.put("B463", airliner);
        types.put("E190", airliner);
        types.put("E195", airliner);
        types.put("E290", airliner);
        types.put("E295", airliner);
        types.put("BCS1", airliner);
        types.put("BCS3", airliner);

        types.put("B741", heavy4E);
        types.put("B742", heavy4E);
        types.put("B743", heavy4E);
        types.put("B744", heavy4E);
        types.put("B74D", heavy4E);
        types.put("B74S", heavy4E);
        types.put("B74R", heavy4E);
        types.put("BLCF", heavy4E);
        types.put("BSCA", heavy4E);
        types.put("B748", heavy4E);

        types.put("B752", heavy2E);
        types.put("B753", heavy2E);

        types.put("B772", heavy2E);
        types.put("B773", heavy2E);
        types.put("B77L", heavy2E);
        types.put("B77W", heavy2E);

        types.put("B701", b707);
        types.put("B703", b707);
        types.put("K35R", b707);
        types.put("K35E", b707);

        types.put("FA20", jetSwept);
        types.put("C680", jetSwept);
        types.put("C68A", jetSwept);
        types.put("YK40", jetSwept);
        types.put("C750", jetSwept);
        types.put("F2TH", jetSwept);
        types.put("FA50", jetSwept);
        types.put("CL30", jetSwept);
        types.put("CL35", jetSwept);
        types.put("F900", jetSwept);
        types.put("CL60", jetSwept);
        types.put("G200", jetSwept);
        types.put("G280", jetSwept);
        types.put("HA4T", jetSwept);
        types.put("FA7X", jetSwept);
        types.put("FA8X", jetSwept);
        types.put("GLF2", jetSwept);
        types.put("GLF3", jetSwept);
        types.put("GLF4", jetSwept);
        types.put("GA5C", jetSwept);
        types.put("GL5T", jetSwept);
        types.put("GLF5", jetSwept);
        types.put("GA6C", jetSwept);
        types.put("GLEX", jetSwept);
        types.put("GL6T", jetSwept);
        types.put("GLF6", jetSwept);
        types.put("GA7C", jetSwept);
        types.put("GA8C", jetSwept);
        types.put("GL7T", jetSwept);
        types.put("E135", jetSwept);
        types.put("E35L", jetSwept);
        types.put("E145", jetSwept);
        types.put("E45X", jetSwept);
        types.put("CRJ1", jetSwept);
        types.put("CRJ2", jetSwept);
        types.put("F28", jetSwept);
        types.put("CRJ7", jetSwept);
        types.put("CRJ9", jetSwept);
        types.put("F70", jetSwept);
        types.put("CRJX", jetSwept);
        types.put("F100", jetSwept);
        types.put("DC91", jetSwept);
        types.put("DC92", jetSwept);
        types.put("DC93", jetSwept);
        types.put("DC94", jetSwept);
        types.put("DC95", jetSwept);
        types.put("MD80", jetSwept);
        types.put("MD81", jetSwept);
        types.put("MD82", jetSwept);
        types.put("MD83", jetSwept);
        types.put("MD87", jetSwept);
        types.put("MD88", jetSwept);
        types.put("MD90", jetSwept);
        types.put("B712", jetSwept);
        types.put("B721", jetSwept);
        types.put("B722", jetSwept);

        types.put("T154", jetSwept);

        types.put("A37", hiPerf);
        types.put("A700", hiPerf);
        types.put("LEOP", hiPerf);
        types.put("ME62", hiPerf);
        types.put("T2", hiPerf);
        types.put("T37", hiPerf);
        types.put("T38", t38);
        types.put("A10", a10);
        types.put("A3", hiPerf);
        types.put("A6", hiPerf);
        types.put("AT3", hiPerf);
        types.put("CKUO", hiPerf);
        types.put("EUFI", typhoon);
        types.put("F1", hiPerf);
        types.put("F111", hiPerf);
        types.put("F117", hiPerf);
        types.put("F14", hiPerf);
        types.put("F15", f15);
        types.put("F16", hiPerf);
        types.put("F18", f18);
        types.put("F18H", f18);
        types.put("F18S", f18);
        types.put("F4", hiPerf);
        types.put("J8A", hiPerf);
        types.put("J8B", hiPerf);
        types.put("JH7", hiPerf);
        types.put("LTNG", hiPerf);
        types.put("M346", hiPerf);
        types.put("METR", hiPerf);
        types.put("MG19", hiPerf);
        types.put("MG25", hiPerf);
        types.put("MG29", hiPerf);
        types.put("MG31", hiPerf);
        types.put("MG44", hiPerf);
        types.put("MIR4", hiPerf);
        types.put("MT2", hiPerf);
        types.put("Q5", hiPerf);
        types.put("S3", hiPerf);
        types.put("S37", hiPerf);
        types.put("SR71", hiPerf);
        types.put("SU15", hiPerf);
        types.put("SU24", hiPerf);
        types.put("SU25", hiPerf);
        types.put("SU27", hiPerf);
        types.put("T22M", hiPerf);
        types.put("T4", hiPerf);
        types.put("TU22", hiPerf);
        types.put("VAUT", hiPerf);
        types.put("Y130", hiPerf);
        types.put("YK28", hiPerf);
        types.put("BE20", twinLarge);

        types.put("C130", c130);
        types.put("C30J", c130);

        types.put("DRON", uav);
        types.put("Q1", uav);
        types.put("Q4", uav);
        types.put("Q9", uav);
        types.put("Q25", uav);
        types.put("HRON", uav);

        types.put("V22F", v22Fast);
        types.put("V22", v22Fast);
        types.put("H64", apache);

        // 4 bladed heavy helicopters
        types.put("H60", blackhawk);
        types.put("S92", blackhawk);
        types.put("NH90", blackhawk);

        // Puma, Super Puma, Oryx, Cougar (ICAO'S AS32 & AS3B & PUMA)
        types.put("AS32", puma);
        types.put("AS3B", puma);
        types.put("PUMA", puma);

        types.put("R22", helicopter);
        types.put("R44", helicopter);
        types.put("R66", helicopter);

        types.put("B1", b1bLancer);
        types.put("B52", b52);
        types.put("C17", c17);
        types.put("C5M", c5);
        types.put("E3TF", e3awacs);
        types.put("E3CF", e3awacs);

        types.put("ULAC", cessna);
        types.put("EV97", cessna);
        types.put("FDCT", cessna);
        types.put("WT9", cessna);
        types.put("PIVI", cessna);
        types.put("FK9", cessna);
        types.put("AVID", cessna);
        types.put("NG5", cessna);
        types.put("PNR3", cessna);
        types.put("TL20", cessna);

        types.put("GND", groundSquare);
        types.put("GRND", groundSquare);
        types.put("SERV", groundSquare);
        types.put("EMER", groundSquare);
        types.put("TWR'", groundTower);


    }

    public static SVGDocument resolve(Aircraft aircraft) {
        return types.getOrDefault(aircraft.getT(),
                (aircraft.getCategory() != null) ? category.getOrDefault(aircraft.getCategory(), airliner) : airliner);
    }

    public static SVGDocument get(Aircraft aircraft) {

        if (aircraft.getT() == null)
            return read(resolvePath("empty"));
        else
            return read(resolvePath(aircraft.getT()));
    }

    private static String resolvePath(String type) {
        return switch (type) {
            case "A306", "A330", "A332", "A333", "A338", "A339", "A359", "A35K", "B752", "B753", "B772", "B773", "B77L",
                 "B77W" -> "/markers/heavy2e.svg";
            case "DC10", "MD11" -> "/markers/md11.svg";
            case "A388" -> "/markers/a380.svg";
            case "B741", "B742", "B743", "B744", "B74D", "B74S", "B74R", "BLCF", "BSCA", "B748" -> "/markers/heavy4e.svg";
            case "B701", "B703", "K35R", "K35E" -> "/markers/b707.svg";
            case "FA20", "C680", "C68A", "YK40", "C750", "F2TH", "FA50", "CL30", "CL35", "F900", "CL60", "G200", "G280",
                 "HA4T", "FA7X", "FA8X", "GLF2", "GLF3", "GLF4", "GA5C", "GL5T", "GLF5", "GA6C", "GLEX", "GL6T", "GLF6",
                 "GA7C", "GA8C", "GL7T", "E135", "E35L", "E145", "E45X", "CRJ1", "CRJ2", "F28", "CRJ7", "CRJ9", "F70",
                 "CRJX", "F100", "DC91", "DC92", "DC93", "DC94", "DC95", "MD80", "MD81", "MD82", "MD83", "MD87", "MD88",
                 "MD90", "B712", "B721", "B722", "T154" -> "/markers/jet_swept.svg";
            case "A37", "A700", "LEOP", "ME62", "T2", "T37", "A3", "A6", "AT3", "CKUO", "F1", "F111", "F117", "F14",
                 "F16", "F4", "J8A", "J8B", "JH7", "LTNG", "M346", "METR", "MG19", "MG25", "MG29", "MG31", "MG44",
                 "MIR4", "MT2", "Q5", "S3", "S37", "SR71", "SU15", "SU24", "SU25", "SU27", "T22M", "T4", "TU22", "VAUT",
                 "Y130", "YK28" -> "/markers/hi_perf.svg";
            case "T38" -> "/markers/t38.svg";
            case "A10" -> "/markers/a10.svg";
            default -> "/markers/airliner.svg";
        };
    }

    private static SVGDocument read(String resource) {
        return SVG_LOADER.load(Resources.getAsStream(resource), null, LOADER_CONTEXT);
    }

}
