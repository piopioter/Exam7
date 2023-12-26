package com.example.personinfo.people.controllers;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.*;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.services.PersonService;
import com.example.personinfo.people.utils.mapper.PersonMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/people")
@Validated
public class PersonController {

    private PersonService personService;
    private PersonMapper personMapper;
    private ModelMapper mapper;

    public PersonController(PersonService personService, PersonMapper personMapper, ModelMapper mapper) {
        this.personService = personService;
        this.personMapper = personMapper;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getPeopleByCriteria(@RequestBody Map<String,String> params,
                                                               @PageableDefault Pageable pageable) {
        Page<Person> personPage = personService.getByCriteria(params, pageable);
        Page<PersonDto> map = personPage.map(x -> personMapper.mapToPersonDto(x));
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid CreatePersonCommand command) {
        PersonType type = command.getClass().getAnnotation(PersonType.class);
        Type value = type.value();
        Person mapped = mapper.map(command, value);
        Person savedPerson = personService.save(mapped);
        PersonDto personDto = personMapper.mapToPersonDto(savedPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping
    public ResponseEntity<PersonDto> updatePerson(@RequestBody @Valid UpdatePersonCommand command) {
        PersonType type = command.getClass().getAnnotation(PersonType.class);
        Type value = type.value();
        Person mapped = mapper.map(command, value);
        Person updatedPerson = personService.update(mapped);
        PersonDto personDto = personMapper.mapToPersonDto(updatedPerson);
        return ResponseEntity.ok(personDto);
    }




}

