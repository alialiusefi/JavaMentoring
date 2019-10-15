package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

public abstract class AbstractRepoTest<T extends Entity> {

    EmbeddedDatabase embeddedDatabase;


}
