package com.ericsson.mapbackend.api.nearbysearch;

import lombok.Data;

import java.util.List;

@Data
public class NearbySearchResult {
    public List<Object> html_attributions;
    public String next_page_token;
    public List<Result> results;
    public String status;
}
