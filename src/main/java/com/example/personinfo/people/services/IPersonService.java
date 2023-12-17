package com.example.personinfo.people.services;

public interface IPersonService<T> {

    T save(T entity);
}
