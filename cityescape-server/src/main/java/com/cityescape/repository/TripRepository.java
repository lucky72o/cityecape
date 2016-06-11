package com.cityescape.repository;

import com.cityescape.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Slava on 23/03/2016.
 */
@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findByName(String tripName);

    @Query(value = "FROM Trip t WHERE t.tripStatus = 'ACTIVE'")
    List<Trip> findAllActiveTrips();
}
