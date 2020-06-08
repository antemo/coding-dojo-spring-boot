package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WeatherResponse {

	@JsonProperty("id")
	private Integer cityId;

	@JsonProperty("name")
	private String cityName;

	private Integer timezone;

	@JsonProperty("dt")
	private Instant calculationTime;

	@JsonProperty("coord")
	private Coordinate coordinate;

	@JsonProperty("weather")
	private final List<WeatherCondition> weatherConditions = new ArrayList<>();

	@JsonProperty("main")
	private MainData mainData;

	private Wind wind;

	private Clouds clouds;

	private Precipitation rain;

	private Precipitation snow;

	@JsonProperty("sys")
	private AdditionalData additionalData;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(final Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(final Integer timezone) {
		this.timezone = timezone;
	}

	public Instant getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(final Instant calculationTime) {
		this.calculationTime = calculationTime;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(final Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public List<WeatherCondition> getWeatherConditions() {
		return Collections.unmodifiableList(weatherConditions);
	}

	public void setWeatherCondition(final Collection<WeatherCondition> weatherConditions) {
		Assert.notNull(weatherConditions, "WeatherCondition collection can't be null");
		Assert.noNullElements(weatherConditions, "WeatherCondition collection can't have null elements");

		this.weatherConditions.clear();
		this.weatherConditions.addAll(weatherConditions);
	}

	public MainData getMainData() {
		return mainData;
	}

	public void setMainData(final MainData mainData) {
		this.mainData = mainData;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(final Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(final Clouds clouds) {
		this.clouds = clouds;
	}

	public Precipitation getRain() {
		return rain;
	}

	public void setRain(final Precipitation rain) {
		this.rain = rain;
	}

	public Precipitation getSnow() {
		return snow;
	}

	public void setSnow(final Precipitation snow) {
		this.snow = snow;
	}

	public AdditionalData getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(final AdditionalData additionalData) {
		this.additionalData = additionalData;
	}
}
