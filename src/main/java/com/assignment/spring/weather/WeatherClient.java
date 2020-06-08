package com.assignment.spring.weather;

import com.assignment.spring.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {

	private final WebClient webClient;

	private final String apiKey;

	@Autowired
	public WeatherClient(final WebClient.Builder webClientBuilder, @Value("${weather.root}") final String apiBaseUrl, @Value("${weather.apiKey}") final String apiKey) {
		webClient = webClientBuilder.baseUrl(apiBaseUrl).build();
		this.apiKey = apiKey;
	}

	public Mono<WeatherResponse> getWeather(final String city) {
		// @formatter:off
		return webClient.get()
				 		.uri(uriBuilder -> uriBuilder.path("/weather")
													 .queryParam("q", city)
													 .queryParam("appid", apiKey)
													 .build())
				 		.retrieve()
				 		.bodyToMono(WeatherResponse.class);
		// @formatter:on
	}

	public Mono<WeatherResponse> getWeather(final Integer city) {
		// @formatter:off
		return webClient.get()
						.uri(uriBuilder -> uriBuilder.path("/weather")
													 .queryParam("id", city)
													 .queryParam("appid", apiKey)
													 .build())
						.retrieve()
						.bodyToMono(WeatherResponse.class);
		// @formatter:on
	}
}
