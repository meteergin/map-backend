package com.ericsson.mapbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NearbySearchRequestDto {
    private double latitude;
    private double longitude;
    private int radius;
}
