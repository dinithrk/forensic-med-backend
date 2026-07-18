package com.forensys.backend.config;

import com.forensys.backend.entity.Role;
import com.forensys.backend.entity.User;
import com.forensys.backend.repository.RoleRepository;
import com.forensys.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedRoles();
        seedUsers();
    }

    private void seedRoles() {
        List<String> rolesToSeed = Arrays.asList(
                "ADMIN", "JMO", "MEDICAL_OFFICER", "LABORATORY_STAFF", "CLERICAL_OFFICER", "POLICE_OFFICER"
        );

        for (String roleName : rolesToSeed) {
            if (roleRepository.findByRoleName(roleName).isEmpty()) {
                Role role = Role.builder().roleName(roleName).build();
                roleRepository.save(role);
            }
        }
    }

    private void seedUsers() {
        String encodedPassword = passwordEncoder.encode("password");

        seedUserIfNotFound("admin", "ADMIN", encodedPassword);
        seedUserIfNotFound("jmo", "JMO", encodedPassword);
        seedUserIfNotFound("doctor", "MEDICAL_OFFICER", encodedPassword);
        seedUserIfNotFound("lab", "LABORATORY_STAFF", encodedPassword);
        seedUserIfNotFound("clerk", "CLERICAL_OFFICER", encodedPassword);
        seedUserIfNotFound("police", "POLICE_OFFICER", encodedPassword);
    }

    private void seedUserIfNotFound(String username, String roleName, String encodedPassword) {
        if (userRepository.findByUserName(username).isEmpty()) {
            Role role = roleRepository.findByRoleName(roleName).orElseThrow();
            User user = User.builder()
                    .userName(username)
                    .password(encodedPassword)
                    .roles(Set.of(role))
                    .build();
            userRepository.save(user);
        }
    }
}
