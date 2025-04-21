package com.adarsh.springjwt;
import com.adarsh.springjwt.models.ERole;
import com.adarsh.springjwt.models.Role;
import com.adarsh.springjwt.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializerAtStartUp {
    
    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                for (ERole roleEnum : ERole.values()) {
                    Role role = new Role(roleEnum);
                    roleRepository.save(role);
                }
                System.out.println("Default roles added to database.");
            } else {
                System.out.println("Roles already exist, skipping initialization.");
            }
        };
    }
}
