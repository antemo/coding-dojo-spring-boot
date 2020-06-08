package com.assignment.spring.web;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

public class BaseControllerTest {

	@LocalServerPort
	private int randomServerPort;

	protected WebTestClient webTestClient;

	private final ReactorClientHttpConnector reactorClientHttpConnector;

	public BaseControllerTest() throws SSLException {
		final SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		final HttpClient httpConnector = HttpClient.create().secure(t -> t.sslContext(sslContext));

		reactorClientHttpConnector = new ReactorClientHttpConnector(httpConnector);
	}

	@BeforeEach
	public void init() throws SSLException {
		webTestClient = WebTestClient.bindToServer(reactorClientHttpConnector).baseUrl("https://localhost:" + randomServerPort + "/dojo").build();
	}
}
