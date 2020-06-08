package com.assignment.spring.web;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.exception.WeatherLogException;
import com.assignment.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(UserController.BASE_USERS_URL)
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	public static final String BASE_USERS_URL = "/users";

	private final UserService userService;

	private final ModelMapper modelMapper;

	@Autowired
	public UserController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAnyAuthority('VIEW_USERS', 'EDIT_USERS')")
	public Flux<UserDto> streamAll() {
		LOGGER.info("Getting all users");

		return userService.getAll().flatMap(user -> Mono.just(modelMapper.map(user, UserDto.class)));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAnyAuthority('VIEW_USERS', 'EDIT_USERS') or #id == principal.id")
	public Mono<UserDto> getById(@PathVariable final Long id) {
		LOGGER.info("Getting user with ID {}", id);

		return userService.findById(id).switchIfEmpty(Mono.error(() -> new WeatherLogException(HttpStatus.NOT_FOUND, "No user with ID " + id)))
						  .flatMap(user -> Mono.just(modelMapper.map(user, UserDto.class)));
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('EDIT_USERS') or #user.id == principal.id")
	public Mono<Void> update(@RequestBody @Valid final UserDto user) {
		LOGGER.info("Updating user with ID {}", user.getId());

		return userService.update(user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('EDIT_USERS')")
	public Mono<Void> delete(@PathVariable final Long id) {
		LOGGER.info("Deleting user with ID {}", id);

		return userService.delete(id);
	}
}
