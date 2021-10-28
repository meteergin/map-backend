package com.ericsson.mapbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NearbySearchResponseDto {
    private double searchedLatitude;
    private double searchedLongitude;
    private int radius;
    private double nearbyLatitude;
    private double nearbyLongitude;
    private String name;
}
