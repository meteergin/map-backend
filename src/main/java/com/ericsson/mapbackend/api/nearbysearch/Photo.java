package com.ericsson.mapbackend.api.nearbysearch;

import lombok.Data;

import java.util.List;

@Data
public class Photo{
    public int height;
    public List<String> html_attributions;
    public String photo_reference;
    public int width;
}
