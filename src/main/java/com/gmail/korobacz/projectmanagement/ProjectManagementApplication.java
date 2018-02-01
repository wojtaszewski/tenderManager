package com.gmail.korobacz.projectmanagement;

import com.gmail.korobacz.projectmanagement.Repository.UserRepository;
import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class ProjectManagementApplication {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init(){
		Users user = new Users(
				"Wojtek",
				"Korobacz",
				"w@w.w",
				passwordEncoder.encode("p"),
				Arrays.asList(
						new Role("ROLE_USER"),
						new Role("ROLE_ADMIN")));

		if (userRepository.findByEmail(user.getEmail()) == null){
			userRepository.save(user);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}
}
