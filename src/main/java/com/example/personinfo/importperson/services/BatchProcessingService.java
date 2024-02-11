package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BatchProcessingService {

    private PersonRepository personRepository;

    public BatchProcessingService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBatch(List<Person> personList){
       personRepository.saveAll(personList);
    }

}


