package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ITagServiceTest {

    @Mock
    public ITagService ITagService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTag() {
        TagDTO expected = new TagDTO(1, "Accesories");
        when(ITagService.getTag(expected.getId())).thenReturn(expected);
        Assert.assertEquals(expected, ITagService.getTag(expected.getId()));
    }

    @Test
    public void addTag() {
        TagDTO expected = new TagDTO(5, "TestTag");
        when(ITagService.addTag(expected)).thenReturn(expected);
        Assert.assertEquals(expected, ITagService.addTag(expected));
    }

    @Test
    public void deleteTag() {
        boolean expected = true;
        when(ITagService.deleteTag(5)).thenReturn(expected);
        Assert.assertTrue(ITagService.deleteTag(5));
    }

    @Test
    public void testDeleteTag() {
        boolean expected = true;
        TagDTO tag = new TagDTO(5, "TestTag");
        when(ITagService.deleteTag(tag)).thenReturn(expected);
        Assert.assertTrue(ITagService.deleteTag(tag));
    }
}