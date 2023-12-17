package com.example.personinfo.people.repositories;

import com.example.personinfo.people.models.Student;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudentRepository extends PersonGenericRepository<Student> {

}
