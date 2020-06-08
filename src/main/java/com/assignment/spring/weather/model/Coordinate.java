package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Coordinate {

	@JsonProperty("lon")
	private BigDecimal latitude;

	@JsonProperty("lat")
	private BigDecimal longitude;

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}
}
