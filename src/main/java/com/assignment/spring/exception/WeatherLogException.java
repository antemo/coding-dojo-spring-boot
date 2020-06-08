package com.assignment.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WeatherLogException extends ResponseStatusException {

	private static final long serialVersionUID = -7134601972757576490L;

	public WeatherLogException(final HttpStatus status) {
		super(status);
	}

	public WeatherLogException(final HttpStatus status, final String reason) {
		super(status, reason);
	}

	public WeatherLogException(final HttpStatus status, final String reason, final Throwable cause) {
		super(status, reason, cause);
	}
}
