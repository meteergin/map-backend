package com.ericsson.mapbackend.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class NearbySearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double searchedLatitude;
    private double searchedLongitude;
    private int radius;
    private double nearbyLatitude;
    private double nearbyLongitude;
    private String name;
}
