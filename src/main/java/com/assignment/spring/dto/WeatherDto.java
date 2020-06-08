package com.assignment.spring.dto;

import java.time.Instant;

public class WeatherDto {

	private String city;

	private String country;

	private Double temperature;

	private String weatherCondition;

	private Instant calculationTime;

	// suppress CPD since it detects getters and setters from entity this DTO represents
	@SuppressWarnings("CPD-START")
	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(final Double temperature) {
		this.temperature = temperature;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(final String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public Instant getCalculationTime() {
		return calculationTime;
	}

	@SuppressWarnings("CPD-END")
	public void setCalculationTime(final Instant calculationTime) {
		this.calculationTime = calculationTime;
	}
}
