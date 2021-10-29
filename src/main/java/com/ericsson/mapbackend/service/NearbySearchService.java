package com.ericsson.mapbackend.service;

import com.ericsson.mapbackend.entity.NearbySearch;
import com.ericsson.mapbackend.repository.NearbySearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NearbySearchService {
    private final NearbySearchRepository nearbySearchRepository;

    public List<NearbySearch> findBySearchedLatitudeAndSearchedLongitudeAndRadius(double searchedLatitude, double searchedLongitude, int radius) {
        return nearbySearchRepository.findBySearchedLatitudeAndSearchedLongitudeAndRadius(searchedLatitude, searchedLongitude, radius);
    }

    public NearbySearch save(NearbySearch nearbySearch) {
        return nearbySearchRepository.save(nearbySearch);
    }
}
