package ru.kriterika.kriterikaserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KinopoiskConfig {

	@Value("${kinopoisk.api.url}")
	private String apiUrl;

	@Value("${kinopoisk.api.key}")
	private String apiKey;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiKey() {
		return apiKey;
	}
}
