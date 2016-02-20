package com.cityescape.service.impl;

import com.cityescape.domain.PoeTag;
import com.cityescape.exception.NoSuchTagException;
import com.cityescape.repository.PoeTagRepository;
import com.cityescape.service.PoeTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Slava on 20/02/2016.
 */

@Service
public class PoeTagServiceImpl implements PoeTagService {

    @Autowired
    private PoeTagRepository poeTagRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PoeTagServiceImpl.class);

    private AtomicLong id = new AtomicLong(0);

    @Override
    public String createTag(String uri, boolean oneTimeOnly) {
        Assert.notNull(uri);

        Long nextId = id.incrementAndGet();
        String ref = createEtag(uri, nextId);
        PoeTag tag = new PoeTag(ref);

        LOGGER.info("New Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getId(), tag.getTag());

        poeTagRepository.save(tag);
        return ref;
    }

    @Override
    public PoeTag getTag(String ref) throws NoSuchTagException {
        PoeTag tag = poeTagRepository.findByTag(ref);
        if (tag == null) {
            throw new NoSuchTagException(ref);
        }

        LOGGER.info("Retrieved the Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getId(), tag.getTag());
        return null;
    }

    @Override
    public void consumeTag(String ref) throws NoSuchTagException {
        PoeTag tag = poeTagRepository.findByTag(ref);
        if (tag == null) {
            throw new NoSuchTagException(ref);
        }

        LOGGER.info("Retrieved the Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getId(), tag.getTag());

        tag.setConsumed(true);
        poeTagRepository.saveAndFlush(tag);
    }

    private String createEtag(final String uri, final Long counter) {
        return DigestUtils.md5DigestAsHex((uri + counter).getBytes());
    }
}
