package com.example.personinfo.people.repositories;

import com.example.personinfo.people.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonGenericRepository<T extends Person> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Override
    Page<T> findAll(Specification<T> spec, Pageable pageable);

}
