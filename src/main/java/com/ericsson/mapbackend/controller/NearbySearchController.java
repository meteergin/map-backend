package com.ericsson.mapbackend.controller;

import com.ericsson.mapbackend.api.nearbysearch.NearbySearchResult;
import com.ericsson.mapbackend.api.nearbysearch.Result;
import com.ericsson.mapbackend.dto.NearbySearchRequestDto;
import com.ericsson.mapbackend.dto.NearbySearchResponseDto;
import com.ericsson.mapbackend.entity.NearbySearch;
import com.ericsson.mapbackend.exception.NearbySearchNotFoundException;
import com.ericsson.mapbackend.service.NearbySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class NearbySearchController {

    private final RestTemplate restTemplate;
    private final NearbySearchService nearbySearchService;

    @Value("${google.api_key}")
    private String apiKey;

    @PostMapping("/nearbysearch")
    public List<NearbySearchResponseDto> getNearbySearch(@RequestBody NearbySearchRequestDto nearbySearchRequestDto) {

        double latitude = nearbySearchRequestDto.getLatitude();
        double longitude = nearbySearchRequestDto.getLongitude();
        int radius = nearbySearchRequestDto.getRadius();
        List<NearbySearchResponseDto> responseList = new ArrayList<>();

        List<NearbySearch> searchList = nearbySearchService.findBySearchedLatitudeAndSearchedLongitudeAndRadius(latitude, longitude, radius);

        if (!searchList.isEmpty()) {
            searchList.forEach(r -> {
                responseList.add(NearbySearchResponseDto
                        .builder()
                        .nearbyLatitude(r.getNearbyLatitude())
                        .nearbyLongitude(r.getNearbyLongitude())
                        .name(r.getName())
                        .searchedLatitude(latitude)
                        .searchedLongitude(longitude)
                        .radius(radius)
                        .build());
            });

            return responseList;
        }

        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&key=%s",
                latitude,
                longitude,
                radius,
                apiKey);

        try {
            ResponseEntity<NearbySearchResult> responseEntity = restTemplate.getForEntity(url, NearbySearchResult.class);

            List<Result> resultList = responseEntity.getBody().getResults();
            if (!resultList.isEmpty()) {
                resultList.forEach(r -> {
                    responseList.add(NearbySearchResponseDto
                            .builder()
                            .nearbyLatitude(r.getGeometry().getLocation().lat)
                            .nearbyLongitude(r.getGeometry().getLocation().lng)
                            .name(r.getName())
                            .searchedLatitude(latitude)
                            .searchedLongitude(longitude)
                            .radius(radius)
                            .build());

                    NearbySearch nearbySearch = new NearbySearch();
                    nearbySearch.setNearbyLatitude(r.getGeometry().getLocation().lat);
                    nearbySearch.setNearbyLongitude(r.getGeometry().getLocation().lng);
                    nearbySearch.setSearchedLongitude(longitude);
                    nearbySearch.setSearchedLatitude(latitude);
                    nearbySearch.setRadius(radius);
                    nearbySearch.setName(r.getName());

                    nearbySearchService.save(nearbySearch);
                });
            }
        } catch (NearbySearchNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nearby search result not found.", ex);
        }

        return responseList;
    }

}
