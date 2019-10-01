package com.epam.esm.service;

import com.java.esm.entity.Entity;
import com.java.esm.repository.CRUDRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Service<T extends CRUDRepo> {

    protected T repository;

    protected Service(T repository){
        this.repository = repository;
    }



}
