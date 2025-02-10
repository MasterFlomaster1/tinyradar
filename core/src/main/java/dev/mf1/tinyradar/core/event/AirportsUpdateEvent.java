package dev.mf1.tinyradar.core.event;

import dev.mf1.tinyradar.core.oa.Airport;

import java.util.List;

public record AirportsUpdateEvent(List<Airport> airports) {
}
