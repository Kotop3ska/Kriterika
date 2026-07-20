package ru.kriterika.kriterikaserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class KinopoiskSearchResponseDto {

	@JsonProperty("keyword")
	private String keyword;

	@JsonProperty("pagesCount")
	private Integer pagesCount;

	@JsonProperty("searchFilmsCountResult")
	private Integer searchFilmsCountResult;

	@JsonProperty("films")
	private List<KinopoiskSearchFilmDto> films;

	public String getKeyword() {
		return keyword;
	}

	public Integer getPagesCount() {
		return pagesCount;
	}

	public Integer getSearchFilmsCountResult() {
		return searchFilmsCountResult;
	}

	public List<KinopoiskSearchFilmDto> getFilms() {
		return films;
	}
}
