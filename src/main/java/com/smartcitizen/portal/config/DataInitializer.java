package com.smartcitizen.portal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartcitizen.portal.model.User;
import com.smartcitizen.portal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner createAdmin() {

        return args -> {

            String adminEmail = "admin@civicresolve.com";

            // Check whether admin already exists
            if (!userRepository.existsByEmail(adminEmail)) {

                User admin = new User();

                admin.setFullname("System Administrator");

                admin.setEmail(adminEmail);

                admin.setPassword(
                        passwordEncoder.encode("Admin@123")
                );

                admin.setPhone("9999999999");

                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "       ADMIN ACCOUNT CREATED"
                );

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "Email    : admin@civicresolve.com"
                );

                System.out.println(
                        "Password : Admin@123"
                );

                System.out.println(
                        "Role     : ADMIN"
                );

                System.out.println(
                        "======================================"
                );

            } else {

                System.out.println(
                        "Admin account already exists."
                );
            }
        };
    }
}