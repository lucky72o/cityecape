package com.cityescape.service;

import com.cityescape.domain.PoeTag;
import com.cityescape.exception.NoSuchTagException;

/**
 * Created by Slava on 20/02/2016.
 */
public interface PoeTagService {

    String createTag(final String uri);
    PoeTag getTag(final String ref) throws NoSuchTagException;
    void consumeTag(final String ref) throws NoSuchTagException;

    void consumeTag(PoeTag poeTag) throws NoSuchTagException;
}
