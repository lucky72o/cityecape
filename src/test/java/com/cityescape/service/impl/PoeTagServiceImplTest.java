package com.cityescape.service.impl;

import com.cityescape.domain.PoeTag;
import com.cityescape.exception.NoSuchTagException;
import com.cityescape.repository.PoeTagRepository;
import com.cityescape.service.PoeTagService;
import com.cityescape.utils.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Slava on 28/02/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PoeTagServiceImplTest {

    @InjectMocks
    PoeTagService poeTagService = new PoeTagServiceImpl();
    @Mock
    private PoeTagRepository poeTagRepositoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreatePoeTag() throws Exception {
        poeTagService.createTag("testUri");
        verify(poeTagRepositoryMock, times(1)).save(any(PoeTag.class));
        verifyNoMoreInteractions(poeTagRepositoryMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfUriNull() throws Exception {
        poeTagService.createTag(null);
    }

    @Test
    public void shouldReturnTagByReference() throws Exception {
        PoeTag poeTag = TestDataHelper.getPoeTag();
        String testTagName = "testTag";

        when(poeTagRepositoryMock.findByTag(testTagName)).thenReturn(poeTag);

        PoeTag actual = poeTagService.getTag(testTagName);

        assertThat(actual).isNotNull();
        assertThat(actual.getTag()).isEqualTo(testTagName);
        assertThat(actual.getConsumed()).isEqualTo(false);

        verify(poeTagRepositoryMock, times(1)).findByTag(testTagName);
        verifyNoMoreInteractions(poeTagRepositoryMock);
    }

    @Test(expected = NoSuchTagException.class)
    public void shouldThrowExceptionIfTagIsNull() throws Exception {
        when(poeTagRepositoryMock.findByTag(anyString())).thenReturn(null);
        poeTagService.getTag("testTag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfTagNameIsNull() throws Exception {
        poeTagService.getTag(null);
    }

    @Test(expected = NoSuchTagException.class)
    public void shouldThrowExceptionWhenConsumeTagByRefAndTagNotFound() throws Exception {
        when(poeTagRepositoryMock.findByTag(anyString())).thenReturn(null);
        poeTagService.getTag("testTag");
    }

    @Test
    public void shouldConsumeTagByRef() throws Exception {
        PoeTag poeTag = TestDataHelper.getPoeTag();
        String testTagName = "testTag";

        when(poeTagRepositoryMock.findByTag(testTagName)).thenReturn(poeTag);

        poeTagService.consumeTag(testTagName);

        verify(poeTagRepositoryMock, times(1)).findByTag(testTagName);
        verify(poeTagRepositoryMock, times(1)).saveAndFlush(any(PoeTag.class));
        verifyNoMoreInteractions(poeTagRepositoryMock);
    }

    @Test
    public void shouldConsumeTag() throws Exception {
        PoeTag poeTag = TestDataHelper.getPoeTag();
        poeTagService.consumeTag(poeTag);

        verify(poeTagRepositoryMock, times(1)).saveAndFlush(any(PoeTag.class));
        verifyNoMoreInteractions(poeTagRepositoryMock);
    }
}