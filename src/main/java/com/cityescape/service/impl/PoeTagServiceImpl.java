package com.cityescape.service.impl;

import com.cityescape.domain.PoeTag;
import com.cityescape.exception.NoSuchTagException;
import com.cityescape.repository.PoeTagRepository;
import com.cityescape.service.PoeTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * Created by Slava on 20/02/2016.
 */

@Service
@Transactional
public class PoeTagServiceImpl implements PoeTagService {

    @Autowired
    private PoeTagRepository poeTagRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PoeTagServiceImpl.class);

    @Override
    public String createTag(String uri) {
        Assert.notNull(uri);

        Long nextId = System.currentTimeMillis() + new Random().nextInt(1000);
        String ref = createEtag(uri, nextId);
        PoeTag tag = new PoeTag(ref);

        LOGGER.info("New Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getId(), tag.getTag());

        poeTagRepository.save(tag);
        return ref;
    }

    @Override
    public PoeTag getTag(String ref) throws NoSuchTagException {
        Assert.notNull(ref);

        PoeTag tag = poeTagRepository.findByTag(ref);
        if (tag == null) {
            throw new NoSuchTagException(ref);
        }

        LOGGER.info("Retrieved the Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getTag());
        return tag;
    }

    @Override
    public void consumeTag(String ref) throws NoSuchTagException {
        Assert.notNull(ref);

        PoeTag tag = poeTagRepository.findByTag(ref);
        if (tag == null) {
            throw new NoSuchTagException(ref);
        }

        LOGGER.info("Retrieved the Tag: EntityId [{}], Consumed [{}], Reference [{}]", tag.getId(), tag.getConsumed(), tag.getTag());

        tag.setConsumed(true);
        poeTagRepository.saveAndFlush(tag);
    }

    @Override
    public void consumeTag(PoeTag poeTag) throws NoSuchTagException {
        Assert.notNull(poeTag);

        LOGGER.info("Retrieved the Tag: EntityId [{}], Consumed [{}], Reference [{}]", poeTag.getId(), poeTag.getConsumed(), poeTag.getTag());

        poeTag.setConsumed(true);
        poeTagRepository.saveAndFlush(poeTag);
    }

    private String createEtag(final String uri, final Long counter) {
        return DigestUtils.md5DigestAsHex((uri + counter).getBytes());
    }
}
