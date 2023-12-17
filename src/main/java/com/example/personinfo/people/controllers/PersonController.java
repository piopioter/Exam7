package com.example.personinfo.people.controllers;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.dto.EmployeeDto;
import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.dto.RetireeDto;
import com.example.personinfo.people.dto.StudentDto;
import com.example.personinfo.people.models.*;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.services.PersonService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;

@RestController
@RequestMapping("/api/v1/people")
@Validated
public class PersonController {

    private PersonService personService;
    private ModelMapper mapper;

    public PersonController(PersonService personService, ModelMapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getPeopleByCriteria(@ModelAttribute SearchCriteria searchCriteria,
                                                               @PageableDefault(size = 100) Pageable pageable) {
        Page<Person> personPage = personService.getByCriteria(searchCriteria, pageable);
        Page<PersonDto> map = personPage.map(this::mapToPerson);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid CreatePersonCommand command) {
        PersonType type = command.getClass().getAnnotation(PersonType.class);
        Type value = type.value();
        Person mapped = mapper.map(command, value);
        Person savedPerson = personService.save(mapped);
        PersonDto personDto = mapToPerson(savedPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping
    public ResponseEntity<PersonDto> updatePerson(@RequestBody @Valid UpdatePersonCommand command) {
        PersonType type = command.getClass().getAnnotation(PersonType.class);
        Type value = type.value();
        Person mapped = mapper.map(command, value);
        Person updatedPerson = personService.update(mapped);
        PersonDto personDto = mapToPerson(updatedPerson);
        return ResponseEntity.ok(personDto);
    }


    private PersonDto mapToPerson(Person person){
        if (person instanceof Employee)
            return mapper.map(person, EmployeeDto.class);
        else if (person instanceof Student)
            return mapper.map(person, StudentDto.class);
        else if (person instanceof Retiree)
            return mapper.map(person, RetireeDto.class);
        else
            throw new IllegalArgumentException("Unknown person type");

    }


}

