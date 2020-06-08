package com.assignment.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * This class serves for customization of error responses, much in a way {@link ControllerAdvice} would do for non-reactive applications.<br>
 * Custom {@link WebExceptionHandler}s can be implemented to further customize exception handling, but for current applications all exceptions will extend {@link ResponseStatusException} which is
 * handled by default with Spring
 */
@Component
@Order(-2) // we set the order of our global error handler to -2 to give it a higher priority than the DefaultErrorWebExceptionHandler which is registered at -1
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

	@Autowired
	public GlobalErrorWebExceptionHandler(final ErrorAttributes errorAttributes, final ResourceProperties resourceProperties, final ApplicationContext applicationContext,
										  final ServerCodecConfigurer serverCodecConfigurer) {
		super(errorAttributes, resourceProperties, applicationContext);
		super.setMessageWriters(serverCodecConfigurer.getWriters());
		super.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	/**
	 * This method serves as decision point on how to handle different kinds of errors.<br>
	 * Current implementation will handle all exceptions in same way.
	 *
	 * @param errorAttributes
	 * @return
	 */
	@SuppressWarnings("PMD.UnusedFormalParameter") // must comply with Spring API
	protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	/**
	 * This method serves for customization of error responses.<br>
	 * Current implementation will use HTTP status from error if existing, using {@link HttpStatus#INTERNAL_SERVER_ERROR} as default if none available
	 *
	 * @param serverRequest
	 * @return
	 */
	private Mono<ServerResponse> renderErrorResponse(final ServerRequest serverRequest) {

		final Map<String, Object> errorPropertiesMap = getErrorAttributes(serverRequest, ErrorAttributeOptions.of(Include.MESSAGE, Include.BINDING_ERRORS));

		final HttpStatus errorStatus = HttpStatus.valueOf((Integer) errorPropertiesMap.getOrDefault("status", 500));

		return ServerResponse.status(errorStatus).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(errorPropertiesMap));
	}
}
