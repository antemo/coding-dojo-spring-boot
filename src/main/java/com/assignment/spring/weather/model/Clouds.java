package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {

	@JsonProperty("all")
	private Integer cloudiness;

	public Integer getCloudiness() {
		return cloudiness;
	}

	public void setCloudiness(final Integer cloudiness) {
		this.cloudiness = cloudiness;
	}
}
