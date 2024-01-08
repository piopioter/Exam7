package com.example.personinfo.people.services;

import com.example.personinfo.people.exceptions.DataConflictException;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.repositories.PersonRepository;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.utils.DynamicSpecification;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class PersonService implements IPersonService<Person> {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public Page<Person> getByCriteria(Map<String, String> params, Pageable pageable) {
        Specification<Person> filter = DynamicSpecification.byCriteria(params);
        return personRepository.findAll(filter, pageable);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person update(Person person) {
        personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found entity to update with id: " + person.getId()));
        try {
            return personRepository.save(person);
        } catch (StaleObjectStateException | ObjectOptimisticLockingFailureException e) {
            throw new DataConflictException("This entity has been modified by another entity");
        }
    }


}
