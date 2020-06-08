package com.assignment.spring.service;

import com.assignment.spring.entity.Weather;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.weather.model.WeatherResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class WeatherService {

	private final WeatherRepository weatherRepository;

	private final ModelMapper modelMapper;

	@Autowired
	public WeatherService(final WeatherRepository weatherRepository, final ModelMapper modelMapper) {
		this.weatherRepository = weatherRepository;
		this.modelMapper = modelMapper;
	}

	public Mono<Weather> create(final WeatherResponse weatherResponse) {
		Assert.notNull(weatherResponse, "WeatherResponse to create Weather from can't be null");

		return weatherRepository.save(modelMapper.map(weatherResponse, Weather.class));
	}

	@Transactional(readOnly = true)
	public Flux<Weather> listEntries(final long offset, final long limit) {
		return weatherRepository.findAll().sort().skip(offset).take(limit);
	}
}
