package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.dto.UserDto;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.services.AuthenticationService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(@RequestBody PaginationDto pagination) {
        return ResponseEntity.ok(userService.getUserPerPage(pagination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<User> u = userService.getUserById(id);
        if (u.isPresent()) {
            return ResponseEntity.ok(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto, encoder));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.loginUser(userDto));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> edit(@Valid @RequestBody UserDto userDto, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        Optional<User> u = userService.getUserById(id);
        if (u.isPresent()) {
            User userDb = u.get();
            userDb.setUserDto(userDto, encoder);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.editUser(userDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<User> u = userService.getUserById(id);
        if (u.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
