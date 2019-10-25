package com.epam.esm.repository;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TagRepoTest extends AbstractRepoTest {
/*

    @Test
    public void findByID() {
        Tag expected = new Tag.TagBuilder(1, "Accesories").getResult();
        assertEquals(expected, tagRepository.findByID(1));
    }

    @Test
    public void findAllByName() {
        List<Tag> expectedTags = Arrays.asList(new Tag.TagBuilder(1, "Accesories").getResult());
        List<Tag> actualTags = tagRepository.query(new FindTagByName("Accesories"));
        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void findAllByCertificateID() {
        List<Tag> expectedTags = Arrays.asList(new Tag.TagBuilder(1, "Accesories").getResult(),
                new Tag.TagBuilder(2, "Food").getResult());
        List<Tag> actualTags = tagRepository.query(new FindTagsByCertificateID(1));
        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void add() {
        Tag expectedTag = new Tag.TagBuilder(6, "TestTag").getResult();
        Tag actual = tagRepository.add(expectedTag);
        assertEquals(expectedTag, actual);
    }

    @Test
    public void delete() {
        Tag testTagToBeDeleted = new Tag.TagBuilder(6, "deleteme").getResult();
        Tag expected = tagRepository.add(testTagToBeDeleted);
        tagRepository.delete(expected);
        assertEquals(0, tagRepository.query(new FindTagByName("deleteme")).size());
    }

    @Test
    public void testDelete() {
        Tag testTagToBeDeleted = new Tag.TagBuilder(7, "deleteme2").getResult();
        tagRepository.add(testTagToBeDeleted);
        tagRepository.delete(new FindTagByName("deleteme2"));
        assertTrue(tagRepository.query(new FindTagByName("deleteme2")).isEmpty());
    }
*/

}