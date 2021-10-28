package com.ericsson.mapbackend;

import com.ericsson.mapbackend.entity.NearbySearch;
import com.ericsson.mapbackend.service.NearbySearchService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class MapBackendApplication implements CommandLineRunner {

    private final NearbySearchService nearbySearchService;

    public static void main(String[] args) {
        SpringApplication.run(MapBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*NearbySearch nearbySearch = new NearbySearch();
        nearbySearch.setSearchedLatitude(-33.8670522);
        nearbySearch.setSearchedLongitude(151.1957362);
        nearbySearch.setRadius(200);
        nearbySearch.setName("Test");
        nearbySearchService.save(nearbySearch);*/
    }
}
