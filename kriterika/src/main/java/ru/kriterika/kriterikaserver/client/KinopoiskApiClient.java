package ru.kriterika.kriterikaserver.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kriterika.kriterikaserver.config.KinopoiskConfig;
import ru.kriterika.kriterikaserver.dto.KinopoiskFilmDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskFilterResponseDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskSearchResponseDto;

@Component
public class KinopoiskApiClient {

	private static final Logger log = LoggerFactory.getLogger(KinopoiskApiClient.class);

	private final RestTemplate restTemplate;
	private final KinopoiskConfig config;

	public KinopoiskApiClient(RestTemplate restTemplate, KinopoiskConfig config) {
		this.restTemplate = restTemplate;
		this.config = config;
	}

	public KinopoiskSearchResponseDto searchFilms(String keyword, Integer page) {
		String url = config.getApiUrl() + "/api/v2.1/films/search-by-keyword?keyword=" + keyword + "&page=" + (page != null ? page : 1);

		HttpHeaders headers = createHeaders();
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<KinopoiskSearchResponseDto> response = restTemplate.exchange(
					url, HttpMethod.GET, entity, KinopoiskSearchResponseDto.class);
			return response.getBody();
		} catch (Exception e) {
			log.error("Ошибка при поиске фильмов: {}", e.getMessage());
			return null;
		}
	}

	public KinopoiskFilmDto getFilmById(Integer id) {
		String url = config.getApiUrl() + "/api/v2.2/films/" + id;

		HttpHeaders headers = createHeaders();
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<KinopoiskFilmDto> response = restTemplate.exchange(
					url, HttpMethod.GET, entity, KinopoiskFilmDto.class);
			return response.getBody();
		} catch (Exception e) {
			log.error("Ошибка при получении фильма {}: {}", id, e.getMessage());
			return null;
		}
	}

	public KinopoiskFilterResponseDto getFilmsByFilter(Integer genreId, Integer countryId, Integer page) {
		StringBuilder url = new StringBuilder(config.getApiUrl() + "/api/v2.2/films?page=" + (page != null ? page : 1));
		if (genreId != null) {
			url.append("&genres=").append(genreId);
		}
		if (countryId != null) {
			url.append("&countries=").append(countryId);
		}

		HttpHeaders headers = createHeaders();
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<KinopoiskFilterResponseDto> response = restTemplate.exchange(
					url.toString(), HttpMethod.GET, entity, KinopoiskFilterResponseDto.class);
			return response.getBody();
		} catch (Exception e) {
			log.error("Ошибка при фильтрации фильмов: {}", e.getMessage());
			return null;
		}
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-KEY", config.getApiKey());
		headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
		return headers;
	}
}
