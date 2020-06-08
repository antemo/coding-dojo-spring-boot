package com.assignment.spring.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherCondition {

	private int id;

	@JsonProperty("main")
	private String group;

	private String description;

	private String icon;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}
}
