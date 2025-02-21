package dev.mf1.tinyradar.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mf1.tinyradar.core.al.AirplanesLiveService;
import dev.mf1.tinyradar.core.al.Response;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

    private final AirplanesLiveService airplanesLiveService = new AirplanesLiveService();

    public void request() {
        request(TinyRadar.pos, TinyRadar.range);
    }

    public void request(WGS84 position, float radius) {
        var r = airplanesLiveService.get(position, radius);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var response = objectMapper.readValue(r, Response.class);

            var filtered = response.getAc().stream()
                    .peek(i -> i.setFlight(i.getFlight() != null && !i.getFlight().trim().isEmpty() ? i.getFlight().trim() : "NO CALLSIGN"))
                    .filter(i -> !i.getType().equals("tisb_other") && !i.getType().equals("tisb_trackfile") && !i.getType().equals("adsb_icao_nt"))
                    .filter(i -> i.getLat() != null && i.getLon() != null)
//                    .filter(i -> Filter.showAirborneOnly && !i.isOnGround())
                    .filter(i -> !"TWR".equals(i.getR()))
                    .toList();

            TinyRadar.BUS.post(new FlightsUpdateEvent(filtered));
        } catch (JsonProcessingException e) {
            throw new TinyRadarException(e);
        }
    }

}
