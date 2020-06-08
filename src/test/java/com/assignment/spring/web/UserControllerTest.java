package com.assignment.spring.web;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.dto.UserRegistrationDto;
import com.assignment.spring.entity.Permission;
import com.assignment.spring.entity.User;
import com.assignment.spring.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import javax.net.ssl.SSLException;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseControllerTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	public UserControllerTest() throws SSLException {
		// needed for documenting exception
	}

	/**
	 * Same test could be repeated for other methods, but there's no point in testing Spring. This test is only to check if security is set up properly
	 */
	@Test
	@DisplayName("Unauthenticated user can't access UserController")
	public void testGetUsersUnauthenticated() {
		webTestClient.get().uri(UserController.BASE_USERS_URL).exchange().expectStatus().isUnauthorized();
	}

	/**
	 * Same test could be repeated for other methods, but there's no point in testing Spring. This test is only to check if security is set up properly
	 */
	@Test
	@WithMockUser
	@DisplayName("Authenticated user without proper permissions can't access UserController methods")
	public void testGetUsersNoPermission() {
		webTestClient.get().uri(UserController.BASE_USERS_URL).exchange().expectStatus().isForbidden();
	}

	@Test
	@WithMockUser(authorities = { "EDIT_USERS" })
	@DisplayName("Authenticated user with proper permissions can get stream of users")
	public void testGetUsers() {
		// @formatter:off
		final Flux<User> responseBody = webTestClient.get()
													 .uri(UserController.BASE_USERS_URL)
													 .exchange()
													 .expectStatus().isOk()
													 .returnResult(User.class).getResponseBody();
		// @formatter:on

		Assertions.assertEquals(1, responseBody.count().block(), "There should only exist single user created with SQL script");
	}

	@Test
	@WithMockUser(authorities = { "EDIT_USERS" })
	@DisplayName("Authenticated user with proper permission can update user")
	public void testUpdateUser() {
		final User user = createUser();
		final UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.setUsername(RandomStringUtils.randomAlphanumeric(10));
		userDto.setFullName(RandomStringUtils.randomAlphanumeric(10));
		userDto.setPermissions(List.of(Permission.EDIT_USERS, Permission.VIEW_WEATHER_LOGS));

		webTestClient.put().uri(UserController.BASE_USERS_URL).body(BodyInserters.fromValue(userDto)).exchange().expectStatus().isOk();

		final User loadedUser = userService.findById(user.getId()).block();
		Assertions.assertEquals(userDto.getUsername(), loadedUser.getUsername(), "Username should match");
		Assertions.assertEquals(userDto.getFullName(), loadedUser.getFullName(), "Full name should match");
		Assertions.assertEquals(userDto.getPermissions(), loadedUser.getPermissions(), "Permissions should match");

		// cleanup, since there are problems with @Transactional tests and R2DBC
		userService.delete(user.getId()).block();
	}

	private User createUser() {
		final UserRegistrationDto registrationDto = new UserRegistrationDto();
		registrationDto.setUserName(RandomStringUtils.randomAlphanumeric(10));
		registrationDto.setFullName(RandomStringUtils.randomAlphanumeric(10));
		registrationDto.setPassword(RandomStringUtils.randomAlphanumeric(10));

		return userService.create(registrationDto).block();
	}

	@Test
	@WithMockUser(authorities = { "EDIT_USERS" })
	@DisplayName("Authenticated user with proper permission can delete user")
	public void testDeleteUser() {
		final User user = createUser();

		webTestClient.delete().uri(UserController.BASE_USERS_URL + "/" + user.getId()).exchange().expectStatus().isOk();
	}
}
