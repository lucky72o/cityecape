package com.cityescape.service;

import com.cityescape.domain.PoeTag;
import com.cityescape.exception.NoSuchTagException;

import java.io.Serializable;

/**
 * Created by Slava on 20/02/2016.
 */
public interface PoeTagService {

    String createTag(final String uri, final boolean oneTimeOnly);
    PoeTag getTag(final String ref) throws NoSuchTagException;
    void consumeTag(final String ref) throws NoSuchTagException;
}
