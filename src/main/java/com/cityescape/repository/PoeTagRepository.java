package com.cityescape.repository;

import com.cityescape.domain.PoeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 20/02/2016.
 */

@Transactional
@Repository
public interface PoeTagRepository extends JpaRepository<PoeTag, Long> {
    PoeTag findByTag(String tag);
}
