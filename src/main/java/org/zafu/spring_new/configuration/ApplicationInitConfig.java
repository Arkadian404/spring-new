package org.zafu.spring_new.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zafu.spring_new.entity.Role;
import org.zafu.spring_new.entity.User;
import org.zafu.spring_new.repo.RoleRepo;
import org.zafu.spring_new.repo.UserRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    UserRepo userRepo;
    RoleRepo roleRepo;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                roleRepo.save(
                        Role.builder().name("USER").description("User role").build());
                Role adminRole = roleRepo.save(
                        Role.builder().name("ADMIN").description("Admin role").build());
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password"))
                        .roles(roles)
                        .build();
                userRepo.save(user);
                log.warn("admin user has been created with default password");
            }
            log.warn("admin has already created");
        };
    }
}
