package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.UserDto;
import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.entities.Role;
import org.hakifiles.api.domain.entities.SecurityUser;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.RoleRepository;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DeckListService deckListService;

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
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent() && principal != null && principal.hasClaim("sub")) {
            return byId.get().getName().equals(principal.getClaim("sub"));
        }
        return false;
    }

    @Override
    public boolean hasUserFromDeckList(String deckListId) {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal.hasClaim("sub")) {
            Optional<User> byName = userRepository.findByName(principal.getClaim("sub"));
            if (byName.isPresent()) {
                Optional<DeckList> deckListById = deckListService.getDeckListById(deckListId);
                if (deckListById.isPresent()) {
                    return byName.get().getUserId().equals(deckListById.get().getUserId());
                }
            }
        }
        return false;
    }
}
