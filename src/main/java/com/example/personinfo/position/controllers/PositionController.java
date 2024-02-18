package com.example.personinfo.position.controllers;

import com.example.personinfo.people.services.EmployeeService;
import com.example.personinfo.position.commands.CreatePositionCommand;
import com.example.personinfo.position.commands.UpdatePositionCommand;
import com.example.personinfo.position.dto.FullPositionDto;
import com.example.personinfo.position.dto.PositionDto;
import com.example.personinfo.position.dto.StatusDto;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.services.PositionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/positions")
@Validated
public class PositionController {

    private PositionService positionService;
    private ModelMapper mapper;
    private EmployeeService employeeService;

    public PositionController(PositionService positionService, ModelMapper mapper, EmployeeService employeeService) {
        this.positionService = positionService;
        this.mapper = mapper;
        this.employeeService = employeeService;
    }

    @GetMapping("/byEmployee/{id}")
    public ResponseEntity<List<PositionDto>> getAllByEmployeeId(@PathVariable("id") Long employeeId) {
        List<PositionDto> positions = positionService.getAllByEmployeeId(employeeId)
                .stream()
                .map(position -> mapper.map(position, PositionDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(positions);
    }

    @PostMapping
    public ResponseEntity<FullPositionDto> createPosition(@RequestBody @Valid CreatePositionCommand command) {
        Position position = mapper.map(command, Position.class);
        position.setEmployee(employeeService.get(command.getEmployeeId()));
        Position createdPosition = positionService.createPosition(position);
        FullPositionDto positionDto = mapper.map(createdPosition, FullPositionDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(positionDto);
    }

    @PutMapping
    public ResponseEntity<FullPositionDto> update(@RequestBody @Valid UpdatePositionCommand command) {
        Position position = mapper.map(command, Position.class);
        position.setEmployee(employeeService.get(command.getEmployeeId()));
        Position updatedPosition = positionService.update(position);
        FullPositionDto positionDto = mapper.map(updatedPosition, FullPositionDto.class);
        return ResponseEntity.ok(positionDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StatusDto> delete(@PathVariable("id") Long id) {
        positionService.delete(id);
        return ResponseEntity.ok(new StatusDto(String.valueOf(id)));
    }
}
