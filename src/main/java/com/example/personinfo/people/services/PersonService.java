package com.example.personinfo.people.services;

import com.example.personinfo.people.exceptions.DataConflictException;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.SearchCriteria;
import com.example.personinfo.people.repositories.PersonRepository;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.utils.GenericSpecificationsBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements IPersonService<Person> {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> getByCriteria(SearchCriteria searchCriteria, Pageable pageable) {
        Specification<Person> filter = filter(searchCriteria);
        return personRepository.findAll(filter, pageable);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person update(Person person) {
        if (!personRepository.existsById(person.getId()))
            throw new ResourceNotFoundException("Not found entity to update with id: " + person.getId());
        try {
            return personRepository.save(person);
        } catch (StaleObjectStateException | ObjectOptimisticLockingFailureException e) {
            throw new DataConflictException("This entity has been modified by another entity");
        }
    }


    private <T> Specification<T> filter(SearchCriteria searchCriteria) {
        GenericSpecificationsBuilder<T> es = buildSpecifications(searchCriteria);
        return es.build();
    }

    private <T> GenericSpecificationsBuilder<T> buildSpecifications(SearchCriteria searchCriteria) {
        return new GenericSpecificationsBuilder<T>()
                .hasType(searchCriteria.getType())
                .hasFirstName(searchCriteria.getFirstName())
                .hasLastName(searchCriteria.getLastName())
                .hasPesel(searchCriteria.getPesel())
                .hasHeightBetween(searchCriteria.getHeightFrom(), searchCriteria.getHeightTo())
                .hasWeightBetween(searchCriteria.getWeightFrom(), searchCriteria.getWeightTo())
                .hasEmail(searchCriteria.getEmail())
                .hasCurrentSalaryBetween(searchCriteria.getCurrentSalaryFrom(), searchCriteria.getCurrentSalaryTo())
                .hasCurrentPosition(searchCriteria.getCurrentPosition())
                .hasEmploymentDateBetween(searchCriteria.getEmploymentDateFrom(), searchCriteria.getEmploymentDateTo())
                .hasUniversityName(searchCriteria.getUniversityName())
                .hasStudyYearBetween(searchCriteria.getStudyYearFrom(), searchCriteria.getStudyYearTo())
                .hasStudyField(searchCriteria.getStudyField())
                .hasScholarshipAmountBetween(searchCriteria.getScholarshipAmountFrom(), searchCriteria.getScholarshipAmountTo())
                .hasPensionAmountBetween(searchCriteria.getPensionAmountFrom(), searchCriteria.getPensionAmountTo())
                .hasYearsWorkedBetween(searchCriteria.getYearsWorkedFrom(), searchCriteria.getYearsWorkedTo());
    }
}
