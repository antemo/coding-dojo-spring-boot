package com.assignment.spring.web;

import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.service.WeatherService;
import com.assignment.spring.weather.WeatherClient;
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

@RestController
@RequestMapping(WeatherController.WEATHER_URL)
public class WeatherController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

	public static final String WEATHER_URL = "/weather";

	private final WeatherClient weatherClient;

	private final WeatherService weatherService;

	private final ModelMapper modelMapper;

	@Autowired
	public WeatherController(final WeatherClient weatherClient, final WeatherService weatherService, final ModelMapper modelMapper) {
		this.weatherClient = weatherClient;
		this.weatherService = weatherService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(params = { "cityName" })
	@ResponseStatus(HttpStatus.OK)
	public Mono<WeatherDto> weather(@RequestParam final String cityName) {
		LOGGER.info("Getting weather data for city {}", cityName);

		return weatherClient.getWeather(cityName).flatMap(weatherService::create).flatMap(weather -> Mono.just(modelMapper.map(weather, WeatherDto.class)));
	}

	@GetMapping(params = { "cityId" })
	@ResponseStatus(HttpStatus.OK)
	public Mono<WeatherDto> weather(@RequestParam final Integer cityId) {
		LOGGER.info("Getting weather data for city with ID {}", cityId);

		return weatherClient.getWeather(cityId).flatMap(weatherService::create).flatMap(weather -> Mono.just(modelMapper.map(weather, WeatherDto.class)));
	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('VIEW_WEATHER_LOGS')")
	public Flux<WeatherDto> listEntries(@RequestParam(defaultValue = "0") final long offset, @RequestParam(defaultValue = "10") final long limit) {
		LOGGER.info("Listing weather logs (offset={},limit={})", offset, limit);

		return weatherService.listEntries(offset, limit).flatMap(weather -> Mono.just(modelMapper.map(weather, WeatherDto.class)));
	}
}
