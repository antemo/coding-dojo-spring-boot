package com.assignment.spring.web;

import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.weather.WeatherClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.time.Instant;

/**
 * TODO this test needs to be finished when R2DBC-H2 supports {@link Instant} type in version 0.9.0.
 * This was last piece of code to be created for this project, so it's too late to change whole project implementation because of this. {@link WeatherController} tests are all skipped until
 * R2DBC-H2 version 0.9.0
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerTest extends BaseControllerTest {

	// @formatter:off
	public static final String OPEN_WEATHER_RESPONSE = "{\"timezone\":7200,\"wind\":{\"speed\":3.1,\"deg\":280},\"clouds\":{\"all\":40}," +
			"\"id\":2759794,\"name\":\"Amsterdam\",\"dt\":1591582457,\"coord\":{\"lon\":4.89,\"lat\":52.37}," +
			"\"weather\":[{\"id\":802,\"description\":\"scattered clouds\",\"icon\":\"03n\",\"main\":\"Clouds\"}]," +
			"\"main\":{\"humidity\":93,\"temp\":284.85,\"feels_like\":282.89,\"pressure\":1011.0,\"temp_min\":283.71,\"temp_max\":285.37,\"sea_level\":null,\"grnd_level\":null}," +
			"\"sys\":{\"country\":\"NL\",\"sunrise\":1591586386,\"sunset\":1591646376}}";
	// @formatter:on

	private static final WeatherDto WEATHER_RESPONSE_DTO = weatherResponse();

	private static MockWebServer mockWebServer;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private WeatherController weatherController;

	public WeatherControllerTest() throws SSLException {
		// needed for documenting exception
	}

	@BeforeAll
	public static void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	private static WeatherDto weatherResponse() {
		final WeatherDto result = new WeatherDto();
		result.setCity("Amsterdam");
		result.setCountry("NL");
		result.setTemperature(284.85);
		result.setWeatherCondition("scattered clouds");
		result.setCalculationTime(Instant.ofEpochSecond(1_591_582_457));

		return result;
	}

	@BeforeEach
	public void init() throws SSLException {
		super.init();

		final WeatherClient weatherClient = new WeatherClient(webClientBuilder, String.format("http://localhost:%s", mockWebServer.getPort()), "API_key_not_used");
		ReflectionTestUtils.setField(weatherController, "weatherClient", weatherClient);
	}

	@Test
	@WithMockUser
	@DisplayName("Test that calling our API will call OpenWeather API")
	@Disabled("This test fails because H2 R2DBC library has problems with Instant type, which should be resolved in version 0.9.0")
	public void testGetWeather() {
		mockWebServer.enqueue(new MockResponse().setBody(OPEN_WEATHER_RESPONSE).addHeader("Content-Type", "application/json"));

		// @formatter:off
		final WeatherDto responseBody =
				webTestClient.get()
							 .uri(uriBuilder -> uriBuilder.path(WeatherController.WEATHER_URL)
														  .queryParam("cityName", "Amsterdam")
														  .build())
							 .exchange()
							 .expectStatus().isOk()
							 .expectBody(WeatherDto.class)
							 .returnResult().getResponseBody();
		// @formatter:on

		Assertions.assertEquals(WEATHER_RESPONSE_DTO.getCity(), responseBody.getCity(), "City should match");
		Assertions.assertEquals(WEATHER_RESPONSE_DTO.getCountry(), responseBody.getCountry(), "Country should match");
		Assertions.assertEquals(WEATHER_RESPONSE_DTO.getTemperature(), responseBody.getTemperature(), "Temperature should match");
		Assertions.assertEquals(WEATHER_RESPONSE_DTO.getWeatherCondition(), responseBody.getWeatherCondition(), "Weather should match");
		Assertions.assertEquals(WEATHER_RESPONSE_DTO.getCalculationTime(), responseBody.getCalculationTime(), "Time should match");
	}

	@AfterAll
	public static void tearDown() throws IOException {
		mockWebServer.shutdown();
	}
}
