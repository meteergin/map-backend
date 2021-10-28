package com.ericsson.mapbackend.repository;

import com.ericsson.mapbackend.entity.NearbySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearbySearchRepository extends JpaRepository<NearbySearch, Long> {
    public List<NearbySearch> findBySearchedLatitudeAndSearchedLongitudeAndRadius(double searchedLatitude, double searchedLongitude, int radius);
}
