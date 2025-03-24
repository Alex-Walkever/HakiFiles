package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getUserPerPage(PaginationDto pagination);

    void deleteUser(Long id);

    Optional<User> getUserById(Long id);

    User saveUser(User user);
}
