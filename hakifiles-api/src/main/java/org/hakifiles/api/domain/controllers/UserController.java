package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUsers(@RequestBody PaginationDto pagination) {
        return ResponseEntity.ok(service.getUserPerPage(pagination));
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<User> u = service.getUserById(id);
        if (u.isPresent()) {
            return ResponseEntity.ok(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        if (user.getDeckList() == null) {
            user.setDeckList(new ArrayList<Long>());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        Optional<User> u = service.getUserById(id);
        if (u.isPresent()) {
            User userDb = u.get();
            userDb.setUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<User> u = service.getUserById(id);
        if (u.isPresent()) {
            service.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
