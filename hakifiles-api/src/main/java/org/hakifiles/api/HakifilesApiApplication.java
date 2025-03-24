package org.hakifiles.api;


import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class HakifilesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HakifilesApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
//            userRepository.save(new User("user", encoder.encode("password"), "user@mail.com", "ROLE_USER"));
//            userRepository.save(new User("admin", encoder.encode("password"), "admin@mail.com", "ROLE_USER,ROLE_ADMIN"));
        };
    }

}
