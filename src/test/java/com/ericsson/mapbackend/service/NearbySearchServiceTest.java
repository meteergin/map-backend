package com.ericsson.mapbackend.service;


import com.ericsson.mapbackend.entity.NearbySearch;
import com.ericsson.mapbackend.repository.NearbySearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NearbySearchServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NearbySearchRepository nearbySearchRepository;

    @Test
    void findBySearchedLatitudeAndSearchedLongitudeAndRadius() {
        NearbySearch nearbySearch = new NearbySearch();
        nearbySearch.setName("test");
        nearbySearch.setNearbyLongitude(1.0);
        nearbySearch.setNearbyLatitude(2.0);
        nearbySearch.setSearchedLatitude(3.0);
        nearbySearch.setSearchedLongitude(4.0);
        nearbySearch.setRadius(100);
        nearbySearch.setId(1L);

        NearbySearch savedNearbySearch = nearbySearchRepository.save(nearbySearch);

        assertEquals(savedNearbySearch, nearbySearchRepository.findBySearchedLatitudeAndSearchedLongitudeAndRadius(
                nearbySearch.getSearchedLatitude(),
                nearbySearch.getSearchedLongitude(),
                nearbySearch.getRadius()).get(0));
    }

    @Test
    void save() {
        NearbySearch nearbySearch = new NearbySearch();
        nearbySearch.setName("test");
        nearbySearch.setNearbyLongitude(1.0);
        nearbySearch.setNearbyLatitude(2.0);
        nearbySearch.setSearchedLatitude(3.0);
        nearbySearch.setSearchedLongitude(4.0);
        nearbySearch.setRadius(100);

        assertEquals(nearbySearch, nearbySearchRepository.save(nearbySearch));
    }

    @Test
    void googlePlacesAPICall() {
        assertFalse(restTemplate.getForObject("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=200&key=AIzaSyB3goBXF4AVFqunFnMZ5i6UVO9RPlyR4is", String.class).isEmpty());
    }
}

