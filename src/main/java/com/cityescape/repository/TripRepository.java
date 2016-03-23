package com.cityescape.repository;

import com.cityescape.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 23/03/2016.
 */
@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findByName(String tripName);
}
