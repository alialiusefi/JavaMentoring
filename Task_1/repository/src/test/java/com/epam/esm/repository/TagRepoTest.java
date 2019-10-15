package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TagRepoTest extends AbstractRepoTest<Tag>{

    @Autowired
    TagRepository tagRepository;

    public TagRepoTest() {
    }

    @Test
    public void findByID() {
        Tag expected = new Tag(1,"Accessories");
        Tag actual = tagRepository.findByID(1);
        assertEquals(expected,actual);
    }


    @Test
    public void add() {
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

    @Test
    public void getFieldsArray() {
    }
}