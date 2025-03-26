package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.UserDto;
import org.hakifiles.api.domain.entities.Role;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.RoleRepository;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public LoginResponseDto loginUser(UserDto userDto) throws AuthenticationException {
        try {
            String name = "";
            if (userDto.getName() != null) {
                name = userDto.getName();
            }
            if (userDto.getEmail() != null && name.isEmpty()) {
                Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
                if (byEmail.isPresent()) {
                    name = byEmail.get().getName();
                }
            }
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, userDto.getPassword()));
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDto(userRepository.findByName(name).get(), token);
        } catch (AuthenticationException e) {
            return new LoginResponseDto(null, "");
        }
    }

    @Override
    public boolean hasUserId(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.isPresent();
    }
}
