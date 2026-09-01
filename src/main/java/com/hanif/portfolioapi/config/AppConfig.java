package com.hanif.portfolioapi.config;

import com.hanif.portfolioapi.model.User;
import com.hanif.portfolioapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Value("${ADMIN_USERNAME}")
    private String ADMIN_USERNAME;

    @Value("${ADMIN_PASSWORD}")
    private String ADMIN_PASSWORD;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args ->  {
            if(userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                User user = User.builder()
                        .username(ADMIN_USERNAME)
                        .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                        .build();

                userRepository.save(user);
            }
        };
    }
}
