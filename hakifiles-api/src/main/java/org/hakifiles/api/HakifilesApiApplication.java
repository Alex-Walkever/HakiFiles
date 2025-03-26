package org.hakifiles.api;


import org.hakifiles.api.domain.entities.Role;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.RoleRepository;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class HakifilesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HakifilesApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            Role userRole = roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);

            User user = new User("admin", encoder.encode("password"), "mail@mail.com", new HashSet<>(), roles);

            userRepository.save(user);
        };
    }

}
