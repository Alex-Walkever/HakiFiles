package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    LoginResponseDto loginUser(UserDto userDto) throws AuthenticationException;

    LoginResponseDto updateToken(Authentication authentication);

    boolean hasUserId(Long userId);

    boolean hasUserFromDeckList(String deckListId);
}
