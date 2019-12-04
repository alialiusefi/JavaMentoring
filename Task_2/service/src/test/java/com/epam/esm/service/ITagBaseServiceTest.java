package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.implementation.TagServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ITagBaseServiceTest {

    @Mock
    public TagServiceImpl ITagServiceImpl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTag() {
        TagDTO expected = new TagDTO(1, "Accesories");
        when(ITagServiceImpl.getByID(expected.getId())).thenReturn(expected);
        Assert.assertEquals(expected, ITagServiceImpl.getByID(expected.getId()));
    }

    @Test
    public void addTag() {
        TagDTO expected = new TagDTO(5, "TestTag");
        when(ITagServiceImpl.add(expected)).thenReturn(expected);
        Assert.assertEquals(expected, ITagServiceImpl.add(expected));
    }

    @Test
    public void deleteTag() {
        boolean expected = true;
        when(ITagServiceImpl.delete(5)).thenReturn(expected);
        Assert.assertTrue(ITagServiceImpl.delete(5));
    }

    @Test
    public void testDeleteTag() {
        boolean expected = true;
        TagDTO tag = new TagDTO(5, "TestTag");
        when(ITagServiceImpl.delete(tag)).thenReturn(expected);
        Assert.assertTrue(ITagServiceImpl.delete(tag));
    }
}