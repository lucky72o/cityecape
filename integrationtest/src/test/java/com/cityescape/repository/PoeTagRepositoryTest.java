package com.cityescape.repository;

import com.cityescape.domain.PoeTag;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by Slava on 23/03/2016.
 */
public class PoeTagRepositoryTest extends AbstractCityEscapeRepositoryTest {

    @Autowired
    private PoeTagRepository poeTagRepository;

    private PoeTag poeTag;
    public static final String POE_TAG = "98998eg77efjhj323jf";

    @Before
    public void setUp() throws Exception {
        poeTag = new PoeTag(POE_TAG);
        poeTagRepository.save(poeTag);
    }

    @Test
    public void shouldFindPoeTagByTag() throws Exception {
        PoeTag result = poeTagRepository.findByTag(POE_TAG);

        assertThat(result).isEqualTo(poeTag);
    }
}