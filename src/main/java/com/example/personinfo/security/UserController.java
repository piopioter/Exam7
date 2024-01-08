package com.example.personinfo.security;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> addUser(@RequestBody CreateUserCommand command) {
        User newUser = modelMapper.map(command, User.class);
        newUser.setPassword(passwordEncoder.encode(command.getPassword()));
        User savedUser = userRepository.save(newUser);

        return ResponseEntity.ok(new UserDto(savedUser.getId(), savedUser.getUserName(),
                savedUser.getEmail(), command.getPassword(), savedUser.getRole()));

    }


}
