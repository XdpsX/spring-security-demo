package com.demo.security;

import com.demo.security.entities.Role;
import com.demo.security.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(RoleRepository roleRepository) {
		return args -> {
			if (!roleRepository.existsByName(Role.ADMIN)) {
				roleRepository.save(Role.builder().name(Role.ADMIN).build());
			}
			if (!roleRepository.existsByName(Role.USER)) {
				roleRepository.save(Role.builder().name(Role.USER).build());
			}
		};
	}

}
