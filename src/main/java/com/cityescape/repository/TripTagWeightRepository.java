package com.cityescape.repository;

import com.cityescape.domain.TripTagWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 10/04/2016.
 */
@Component
@Transactional
public interface TripTagWeightRepository extends JpaRepository<TripTagWeight,Long> {

}
