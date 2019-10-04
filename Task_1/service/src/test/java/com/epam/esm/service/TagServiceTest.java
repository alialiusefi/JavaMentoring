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
public class TagServiceTest {

    @Mock
    public TagService tagService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTag() {
        TagDTO expected = new TagDTO(1, "Accesories");
        when(tagService.getTag(expected.getId())).thenReturn(expected);
        Assert.assertEquals(expected, tagService.getTag(expected.getId()));
    }

    @Test
    public void addTag() {
        TagDTO expected = new TagDTO(5, "TestTag");
        when(tagService.addTag(expected)).thenReturn(expected);
        Assert.assertEquals(expected, tagService.addTag(expected));
    }

    @Test
    public void deleteTag() {
        boolean expected = true;
        when(tagService.deleteTag(5)).thenReturn(expected);
        Assert.assertTrue(tagService.deleteTag(5));
    }

    @Test
    public void testDeleteTag() {
        boolean expected = true;
        TagDTO tag = new TagDTO(5, "TestTag");
        when(tagService.deleteTag(tag)).thenReturn(expected);
        Assert.assertTrue(tagService.deleteTag(tag));
    }
}