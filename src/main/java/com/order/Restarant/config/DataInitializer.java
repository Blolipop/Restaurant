package com.order.Restarant.config;


import com.order.Restarant.Repo.login.RoleRepository;
import com.order.Restarant.model.login.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepo) {
        return args -> {
            if (!roleRepo.existsByName("ROLE_USER")) {
                roleRepo.save(new Role("ROLE_USER"));
            }
            if (!roleRepo.existsByName("ROLE_ADMIN")) {
                roleRepo.save(new Role("ROLE_ADMIN"));
            }
        };
    }
}
