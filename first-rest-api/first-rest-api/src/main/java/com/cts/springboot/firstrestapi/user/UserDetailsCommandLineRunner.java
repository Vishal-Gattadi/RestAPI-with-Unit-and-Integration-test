package com.cts.springboot.firstrestapi.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserDetailsRepository repository;

	@Override
	public void run(String... args) throws Exception {
		//logger.info(Arrays.toString(args));// this returns an empty array[].
		
		repository.save(new UserDetails("Vishal", "Admin"));
		repository.save(new UserDetails("Gattadi", "Admin"));
		repository.save(new UserDetails("John", "User"));

		//List<UserDetails> users = repository.findAll(); - returns all the UserDetails
		List<UserDetails> users = repository.findByRole("Admin");
		users.forEach(user -> logger.info(user.toString()));
	}

}

/*
 * CommandLineRunner: Interface used to indicate that a bean should run when it
 * is contained with in a SpringApplication.
 */
