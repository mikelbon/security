package com.uncledave.springsecurity.utils;

import com.uncledave.springsecurity.model.Authority;
import com.uncledave.springsecurity.model.User;
import com.uncledave.springsecurity.repositories.AuthorityRepository;
import com.uncledave.springsecurity.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public Runner(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.authorityRepository.count() == 0) {
            this.authorityRepository.saveAll(List.of(
                    new Authority(AuthorityName.ADMIN),
                    new Authority(AuthorityName.READ),
                    new Authority(AuthorityName.WRITE)
            ));
        }
        if(this.userRepository.count() == 0) {
            var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.userRepository.saveAll(List.of(
                    new User("uncledave",  encoders.encode("UncleDave123"), List.of(this.authorityRepository.findByName(AuthorityName.ADMIN).get())),
                    new User("john", "John123", List.of(this.authorityRepository.findByName(AuthorityName.READ).get())),
                    new User("jane", "Jane123", List.of(this.authorityRepository.findByName(AuthorityName.WRITE).get()))
            ));
        }
    }
}
