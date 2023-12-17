package com.example.personinfo.people.repositories;

import com.example.personinfo.people.models.Person;
import jakarta.transaction.Transactional;

@Transactional
public interface PersonRepository extends PersonGenericRepository<Person>{
}
