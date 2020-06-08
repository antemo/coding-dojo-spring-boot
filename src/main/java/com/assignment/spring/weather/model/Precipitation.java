package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used for {@code rain} and {@code snow} API response, since model is same
 */
public class Precipitation {

	@JsonProperty("1h")
	private Integer lastHour;

	@JsonProperty("3h")
	private Integer last3Hours;

	public Integer getLastHour() {
		return lastHour;
	}

	public void setLastHour(final Integer lastHour) {
		this.lastHour = lastHour;
	}

	public Integer getLast3Hours() {
		return last3Hours;
	}

	public void setLast3Hours(final Integer last3Hours) {
		this.last3Hours = last3Hours;
	}
}
