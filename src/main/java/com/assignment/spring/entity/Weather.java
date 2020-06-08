package com.assignment.spring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Table("weather_log")
public class Weather implements Comparable<Weather> {

	@Id
	@Column("id")
	private Long id;

	@Column("city")
	private String city;

	@Column("country")
	private String country;

	@Column("temperature")
	private Double temperature;

	@Column("weather_condition")
	private String weatherCondition;

	@Column("calculation_time")
	private Instant calculationTime;

	public Long getId() {
		return id;
	}

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

	public void setCalculationTime(final Instant calculationTime) {
		this.calculationTime = calculationTime;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		final Weather weather = (Weather) other;
		return Objects.equals(getCity(), weather.getCity()) && Objects.equals(getCountry(), weather.getCountry()) && Objects.equals(calculationTime, weather.getCalculationTime());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCity(), getCountry(), getCalculationTime());
	}

	@Override
	public int compareTo(final Weather other) {
		return calculationTime.compareTo(other.calculationTime);
	}
}
