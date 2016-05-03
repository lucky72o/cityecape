package com.cityescape.web.support;

import com.cityescape.domain.AbstractEntity;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Slava on 28/03/2016.
 */
public abstract class AbstractTransformer<T extends ResourceSupport, D extends AbstractEntity> {

    abstract public D toEntity(T t);
}
