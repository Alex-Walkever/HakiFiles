package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUserPerPage(PaginationDto pagination);

    void deleteUser(Long id);

    Optional<User> getUserById(Long id);

    User saveUser(User user);
}
