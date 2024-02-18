package com.example.personinfo.people.controllers;

import com.example.personinfo.people.utils.mapperentity.CommandToEntityMapper;
import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.*;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.services.PersonService;
import com.example.personinfo.people.utils.mapperdto.EntityToDtoMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/people")
@Validated
public class PersonController {

    private PersonService personService;
    private EntityToDtoMapper entityToDtoMapper;
    private CommandToEntityMapper mapper;

    public PersonController(PersonService personService, EntityToDtoMapper entityToDtoMapper, CommandToEntityMapper mapper) {
        this.personService = personService;
        this.entityToDtoMapper = entityToDtoMapper;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getPeopleByCriteria(@RequestBody Map<String,String> params,
                                                               @PageableDefault Pageable pageable) {
        Page<Person> personPage = personService.getByCriteria(params, pageable);
        Page<PersonDto> map = personPage.map(x -> entityToDtoMapper.mapEntityToDto(x));
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid CreatePersonCommand command) {
        Person mapped = mapper.mapToEntity(command, command.getType());
        Person savedPerson = personService.save(mapped);
        PersonDto personDto = entityToDtoMapper.mapEntityToDto(savedPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping
    public ResponseEntity<PersonDto> updatePerson(@RequestBody @Valid UpdatePersonCommand command) {
        Person mapped = mapper.mapToEntity(command, command.getType());
        Person updatedPerson = personService.update(mapped);
        PersonDto personDto = entityToDtoMapper.mapEntityToDto(updatedPerson);
        return ResponseEntity.ok(personDto);
    }




}

