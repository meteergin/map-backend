package com.ericsson.mapbackend.api.nearbysearch;

import lombok.Data;

@Data
public class Geometry{
    public Location location;
    public Viewport viewport;
}
