package com.assignment.spring.service;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.dto.UserRegistrationDto;
import com.assignment.spring.entity.User;
import com.assignment.spring.exception.WeatherLogException;
import com.assignment.spring.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for operation on {@link User}s. It implements {@link ReactiveUserDetailsService} for easy integration with Spring Security
 */
@Service
@Transactional
public class UserService implements ReactiveUserDetailsService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(final UserRepository userRepository, final ModelMapper modelMapper, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public Mono<User> create(final UserRegistrationDto userDto) {
		Assert.notNull(userDto, "UserRegistrationDto to create User from can't be null");

		// @formatter:off
		return Mono.just(modelMapper.map(userDto, User.class))
				   .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
				   .flatMap(userRepository::save)
				   .onErrorResume(this::handleCreateError);
		// @formatter:on
	}

	private Mono<User> handleCreateError(final Throwable throwable) {
		if (throwable instanceof DataIntegrityViolationException) {
			return Mono.error(() -> new WeatherLogException(HttpStatus.BAD_REQUEST, "User with given username already exists!", throwable));
		} else {
			return Mono.error(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error storing user to database", throwable));
		}
	}

	@Transactional(readOnly = true)
	public Flux<User> getAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Mono<User> findById(final Long id) {
		return userRepository.findById(id);
	}

	public Mono<Void> update(final UserDto userDto) {
		// @formatter:off
		return userRepository.findById(userDto.getId())
					  .switchIfEmpty(Mono.error(() -> new WeatherLogException(HttpStatus.NOT_FOUND, "No user with ID " + userDto.getId())))
					  .doOnNext(user -> modelMapper.map(userDto, user))
					  .flatMap(userRepository::save)
					  .then();
		// @formatter:on
	}

	public Mono<Void> delete(final Long id) {
		return userRepository.deleteById(id);
	}

	public Mono<UserDetails> findByUsername(final String username) {
		return userRepository.findByUsername(username).flatMap(user -> Mono.just((UserDetails) user));
	}
}
