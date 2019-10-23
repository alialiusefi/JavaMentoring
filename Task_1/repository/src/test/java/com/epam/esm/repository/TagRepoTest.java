package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specfication.FindTagByName;
import com.epam.esm.repository.specfication.FindTagsByCertificateID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class TagRepoTest extends AbstractRepoTest {

    @Test
    public void findByID() {
        Tag expected = new Tag(1, "Accesories");
        assertEquals(expected, tagRepository.findByID(1));
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
        Tag expectedTag = new Tag(6, "TestTag");
        Tag actual = tagRepository.add(expectedTag);
        assertEquals(expectedTag, actual);
    }

    @Test
    public void delete() {
        Tag testTagToBeDeleted = new Tag(6, "deleteme");
        Tag expected = tagRepository.add(testTagToBeDeleted);
        tagRepository.delete(expected);
        assertEquals(0, tagRepository.query(new FindTagByName("deleteme")).size());
    }

    @Test
    public void testDelete() {
        Tag testTagToBeDeleted = new Tag(7, "deleteme2");
        tagRepository.add(testTagToBeDeleted);
        tagRepository.delete(new FindTagByName("deleteme2"));
        assertTrue(tagRepository.query(new FindTagByName("deleteme2")).isEmpty());
    }

}