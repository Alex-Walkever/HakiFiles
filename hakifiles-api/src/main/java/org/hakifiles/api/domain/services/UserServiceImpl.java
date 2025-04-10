package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.LoginResponseDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.dto.UserDto;
import org.hakifiles.api.domain.entities.Role;
import org.hakifiles.api.domain.entities.SecurityUser;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.RoleRepository;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getUserPerPage(PaginationDto pagination) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return (List<User>) userRepository.findAll(p).stream().toList();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User saveUser(UserDto userDto, PasswordEncoder encoder) {
        User user = new User();
        user.setUserDto(userDto, encoder);
        Optional<Role> role = roleRepository.findByAuthority("USER");
        role.ifPresent(user::addAuthority);
        user.setDeckList(new HashSet<>());

        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<Role> getByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
