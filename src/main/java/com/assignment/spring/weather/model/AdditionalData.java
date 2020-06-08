package com.assignment.spring.weather.model;

import java.time.Instant;

public class AdditionalData {

	private String country;

	private Instant sunrise;

	private Instant sunset;

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public Instant getSunrise() {
		return sunrise;
	}

	public void setSunrise(final Instant sunrise) {
		this.sunrise = sunrise;
	}

	public Instant getSunset() {
		return sunset;
	}

	public void setSunset(final Instant sunset) {
		this.sunset = sunset;
	}
}
