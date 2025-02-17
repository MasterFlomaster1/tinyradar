package dev.mf1.tinyradar.core.al;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft implements Cloneable {

    private String hex;
    private String type;

    @Nullable
    private String flight;

    @Nullable
    private String r;

    @Nullable
    private String t;

    @Nullable
    private String desc;

    @Nullable
    private String ownOp;

    @Nullable
    @JsonProperty("alt_baro")
    private String altBaro;

    private Double gs;

    @Nullable
    private Integer ias;

    @Nullable
    private Integer tas;

    @Nullable
    private Double mach;
    private int wd;
    private int ws;
    private int oat;
    private int tat;

    @Nullable
    private Double track;

    @JsonProperty("track_rate")
    private double trackRate;

    private double roll;

    @JsonProperty("mag_heading")
    private Double magHeading;

    @JsonProperty("true_heading")
    private Double trueHeading;

    @JsonProperty("baro_rate")
    private Integer baroRate;

    private String squawk;
    private String emergency;
    private String category;

    @JsonProperty("nav_altitude_mcp")
    private int navAltitudeMcp;

    private Double lat;
    private Double lon;

    @JsonProperty("seen_pos")
    private double seenPos;

    private int alert;
    private int spi;
    private int messages;
    private double seen;
    private double rssi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return Objects.equals(hex, aircraft.hex) && Objects.equals(flight, aircraft.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hex, flight);
    }

    public boolean isOnGround() {
        return "ground".equals(altBaro);
    }

    public int getAnySpeed() {
        return (ias != null) ? ias : (tas != null) ? tas : (gs != null) ? gs.intValue() : 0;
    }

    public int getAnyHeading() {
        return (track != null) ? track.intValue() : (trueHeading != null) ? trueHeading.intValue()
                : (magHeading != null) ? magHeading.intValue() : 0;
    }

    @Override
    public Aircraft clone() {
        try {
            return (Aircraft) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
