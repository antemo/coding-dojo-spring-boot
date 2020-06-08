package com.assignment.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class ApplicationTests {

	@Test
	@DisplayName("Check application context starts up OK")
	public void contextLoads() {
		// no code needed
	}

	/**
	 *	Only integration tests are added to this project, since there is no application logic that can be unit tested.
	 */
}
