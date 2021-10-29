package com.ericsson.mapbackend.service;

import com.ericsson.mapbackend.api.nearbysearch.NearbySearchResult;
import com.ericsson.mapbackend.entity.NearbySearch;
import com.ericsson.mapbackend.repository.NearbySearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class NearbySearchService {
    private final NearbySearchRepository nearbySearchRepository;
    private final RestTemplate restTemplate;

    public List<NearbySearch> findBySearchedLatitudeAndSearchedLongitudeAndRadius(double searchedLatitude, double searchedLongitude, int radius) {
        return nearbySearchRepository.findBySearchedLatitudeAndSearchedLongitudeAndRadius(searchedLatitude, searchedLongitude, radius);
    }

    public NearbySearch save(NearbySearch nearbySearch) {
        return nearbySearchRepository.save(nearbySearch);
    }

    public ResponseEntity<NearbySearchResult> googlePlacesAPICall(String url) {
        return restTemplate.getForEntity(url, NearbySearchResult.class);
    }
}
