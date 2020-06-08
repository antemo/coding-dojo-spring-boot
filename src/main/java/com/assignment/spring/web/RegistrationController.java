package com.assignment.spring.web;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.dto.UserRegistrationDto;
import com.assignment.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class RegistrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	public static final String REGISTRATION_URL = "/register";

	private final UserService userService;

	private final ModelMapper modelMapper;

	@Autowired
	public RegistrationController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@PostMapping(REGISTRATION_URL)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<UserDto> register(@RequestBody @Valid final UserRegistrationDto newUser) {
		LOGGER.info("Creating user {}", newUser.getUserName());

		return userService.create(newUser).flatMap(user -> Mono.just(modelMapper.map(user, UserDto.class)));
	}
}
