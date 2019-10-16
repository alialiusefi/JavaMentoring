package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specfication.FindTagByName;
import com.epam.esm.repository.specfication.FindTagsByCertificateID;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TagRepoTest extends AbstractRepoTest {

    @Test
    public void findByID() {
        assertTrue(true);
    }

    @Test
    public void findAllByName() {
        List<Tag> expectedTags = Arrays.asList(new Tag(1, "Accesories"));
        List<Tag> actualTags = tagRepository.query(new FindTagByName("Accesories"));
        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void findAllByCertificateID() {
        List<Tag> expectedTags = Arrays.asList(new Tag(1, "Accesories"), new Tag(2, "Food"));
        List<Tag> actualTags = tagRepository.query(new FindTagsByCertificateID(1));
        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void add() {
        Tag expectedTag = new Tag();
        expectedTag.setName("TestTag");
        Tag actual = tagRepository.add(expectedTag);
        assertEquals(expectedTag, actual);
    }

    @Test
    public void update() {

    }

    @Test
    public void query() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testDelete() {
    }

}