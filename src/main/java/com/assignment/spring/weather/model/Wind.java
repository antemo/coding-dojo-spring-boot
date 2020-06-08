package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {

	private Double speed;

	@JsonProperty("deg")
	private Integer direction;

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(final Double speed) {
		this.speed = speed;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(final Integer direction) {
		this.direction = direction;
	}
}
