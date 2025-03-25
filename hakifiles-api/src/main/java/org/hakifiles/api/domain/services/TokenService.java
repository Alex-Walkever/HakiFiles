package org.hakifiles.api.domain.services;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateJwt(Authentication auth);
}
