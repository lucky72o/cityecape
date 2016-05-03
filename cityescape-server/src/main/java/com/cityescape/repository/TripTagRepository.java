package com.cityescape.repository;

import com.cityescape.domain.TripTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 15/02/2016.
 */
@Repository
@Transactional
public interface TripTagRepository extends JpaRepository<TripTag,Long> {

    TripTag findByTag(String tag);
}
