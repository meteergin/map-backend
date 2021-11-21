package com.meteergin.mapbackend.controller;

import com.meteergin.mapbackend.api.nearbysearch.NearbySearchResult;
import com.meteergin.mapbackend.api.nearbysearch.Result;
import com.meteergin.mapbackend.dto.NearbySearchResponseDto;
import com.meteergin.mapbackend.entity.NearbySearch;
import com.meteergin.mapbackend.exception.NearbySearchNotFoundException;
import com.meteergin.mapbackend.service.NearbySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class NearbySearchController {

    private final NearbySearchService nearbySearchService;

    @Value("${google.api_key}")
    private String apiKey;

    @GetMapping("/nearbysearch")
    public List<NearbySearchResponseDto> getNearbySearch(
            @RequestParam(required = true) double latitude,
            @RequestParam(required = true) double longitude,
            @RequestParam(required = true) int radius) {

        List<NearbySearchResponseDto> responseList = new ArrayList<>();

        // Check if the same request has been made before.
        // If the returned list is full as a result of the database query, return the list without querying the REST API.
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

        // If the returned list is empty as a result of the database query, query to the REST API, save the response to the database.
        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&key=%s",
                latitude,
                longitude,
                radius,
                apiKey);

        try {
            ResponseEntity<NearbySearchResult> responseEntity = nearbySearchService.googlePlacesAPICall(url);

            if(null == responseEntity || null == responseEntity.getBody()){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nearby search result not found.");
            }

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
