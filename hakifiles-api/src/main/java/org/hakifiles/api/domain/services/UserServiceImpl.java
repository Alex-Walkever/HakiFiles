package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.SecurityUser;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getUserPerPage(PaginationDto pagination) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return (List<User>) repository.findAll(p).stream().toList();
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
                .findByName(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
