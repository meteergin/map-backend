package com.ericsson.mapbackend.api.nearbysearch;

import lombok.Data;

import java.util.List;

@Data
public class Result{
    public Geometry geometry;
    public String icon;
    public String icon_background_color;
    public String icon_mask_base_uri;
    public String name;
    public List<Photo> photos;
    public String place_id;
    public String reference;
    public String scope;
    public List<String> types;
    public String vicinity;
    public String business_status;
    public OpeningHours opening_hours;
    public PlusCode plus_code;
    public double rating;
    public int user_ratings_total;
    public int price_level;
    public boolean permanently_closed;
}
