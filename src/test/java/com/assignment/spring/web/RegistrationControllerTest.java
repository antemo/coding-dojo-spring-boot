package com.assignment.spring.web;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.dto.UserRegistrationDto;
import com.assignment.spring.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.BodyInserters;

import javax.net.ssl.SSLException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest extends BaseControllerTest {

	@Autowired
	private UserService userService;

	public RegistrationControllerTest() throws SSLException {
		// needed for documenting exception
	}

	@Test
	@DisplayName("Test that new user can be successfully created")
	public void testRegistration() {
		final UserRegistrationDto registrationDto = new UserRegistrationDto();
		registrationDto.setUserName(RandomStringUtils.randomAlphanumeric(10));
		registrationDto.setFullName(RandomStringUtils.randomAlphanumeric(10));
		registrationDto.setPassword(RandomStringUtils.randomAlphanumeric(10));

		// @formatter:off
		final UserDto result =
				webTestClient.post()
							 .uri(RegistrationController.REGISTRATION_URL)
							 .body(BodyInserters.fromValue(registrationDto))
							 .exchange()
							 .expectStatus().isCreated()
							 .expectBody(UserDto.class)
							 .returnResult().getResponseBody();
		// @formatter:on

		Assertions.assertEquals(registrationDto.getUserName(), result.getUsername(), "Username should match");
		Assertions.assertEquals(registrationDto.getFullName(), result.getFullName(), "Full name should match");
		Assertions.assertNotNull(result.getId(), "ID should not be null");
		Assertions.assertTrue(result.getPermissions().isEmpty(), "Permissions list should be empty");

		// cleanup, since there are problems with @Transactional tests and R2DBC
		userService.delete(result.getId()).block();
	}

	@Test
	@DisplayName("Test that new user can't have same username as existing user'")
	public void testRegistrationFail() {
		final UserRegistrationDto registrationDto = new UserRegistrationDto();
		// based on initialization SQL script
		registrationDto.setUserName("admin");
		registrationDto.setFullName(RandomStringUtils.randomAlphanumeric(10));
		registrationDto.setPassword(RandomStringUtils.randomAlphanumeric(10));

		// @formatter:off
		webTestClient.post()
					 .uri(RegistrationController.REGISTRATION_URL)
					 .body(BodyInserters.fromValue(registrationDto))
					 .exchange()
					 .expectStatus().isBadRequest();
		// @formatter:on

	}
}
