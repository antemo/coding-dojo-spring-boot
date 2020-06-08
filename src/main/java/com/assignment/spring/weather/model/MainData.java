package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainData {

	@JsonProperty("temp")
	private Double temperature;

	@JsonProperty("feels_like")
	private Double feelsLikeTemperature;

	@JsonProperty("pressure")
	private Double atmosphericPressure;

	private Integer humidity;

	@JsonProperty("temp_min")
	private Double minimumTemperature;

	@JsonProperty("temp_max")
	private Double maximumTemperature;

	@JsonProperty("sea_level")
	private Double seaLevelPressure;

	@JsonProperty("grnd_level")
	private Double groundLevelPressure;

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(final Double temperature) {
		this.temperature = temperature;
	}

	public Double getFeelsLikeTemperature() {
		return feelsLikeTemperature;
	}

	public void setFeelsLikeTemperature(final Double feelsLikeTemperature) {
		this.feelsLikeTemperature = feelsLikeTemperature;
	}

	public Double getAtmosphericPressure() {
		return atmosphericPressure;
	}

	public void setAtmosphericPressure(final Double atmosphericPressure) {
		this.atmosphericPressure = atmosphericPressure;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(final Integer humidity) {
		this.humidity = humidity;
	}

	public Double getMinimumTemperature() {
		return minimumTemperature;
	}

	public void setMinimumTemperature(final Double minimumTemperature) {
		this.minimumTemperature = minimumTemperature;
	}

	public Double getMaximumTemperature() {
		return maximumTemperature;
	}

	public void setMaximumTemperature(final Double maximumTemperature) {
		this.maximumTemperature = maximumTemperature;
	}

	public Double getSeaLevelPressure() {
		return seaLevelPressure;
	}

	public void setSeaLevelPressure(final Double seaLevelPressure) {
		this.seaLevelPressure = seaLevelPressure;
	}

	public Double getGroundLevelPressure() {
		return groundLevelPressure;
	}

	public void setGroundLevelPressure(final Double groundLevelPressure) {
		this.groundLevelPressure = groundLevelPressure;
	}
}
