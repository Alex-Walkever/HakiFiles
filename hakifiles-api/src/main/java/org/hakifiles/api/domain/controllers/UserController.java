package org.hakifiles.api.domain.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.dto.ResponseGetUserDTO;
import org.hakifiles.api.domain.dto.UserDto;
import org.hakifiles.api.domain.entities.Role;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.services.AuthenticationService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController()
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder encoder;

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @GetMapping()
    public ResponseEntity<List<ResponseGetUserDTO>> getUsers(@RequestBody PaginationDto pagination) {
        List<ResponseGetUserDTO> response = new ArrayList<>();
        List<User> userPerPage = userService.getUserPerPage(pagination);
        for (User u : userPerPage) {
            response.add(new ResponseGetUserDTO(u));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> isAuthenticated(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(userService.getUserByName(authentication.getName()));
    }

    @GetMapping("/details/{id}")
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

        User user = userService.saveUser(userDto, encoder);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.loginUser(userDto));
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.loginUser(userDto));
    }

    @PostMapping("/roles/add/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAuthorityToUser(@RequestBody String authority, @PathVariable Long id) {
        Optional<User> userById = userService.getUserById(id);
        if (userById.isPresent()) {
            Optional<Role> byAuthority = userService.getByAuthority(authority);
            if (byAuthority.isPresent()) {
                User user = userById.get();
                user.addAuthority(byAuthority.get());
                userService.editUser(user);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/roles/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeAuthorityToUser(@RequestBody String authority, @PathVariable Long id) {
        Optional<User> userById = userService.getUserById(id);
        if (userById.isPresent()) {
            User user = userById.get();
            user.removeAuthority(authority);
            userService.editUser(user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("this.getAuthenticationService().hasUserId( #id ) or hasRole('ADMIN')")
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
    @PreAuthorize("this.getAuthenticationService().hasUserId( #id ) or hasRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<User> u = userService.getUserById(id);
        if (u.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
