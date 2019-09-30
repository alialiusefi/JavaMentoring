package com.epam.esm.service;

import com.java.esm.repository.CRUDRepo;

public abstract class Service<T extends CRUDRepo> {

    protected T repository;

    protected Service(T repository){
        this.repository = repository;
    }



}
