package ru.kriterika.kriterikaserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class KinopoiskFilterResponseDto {

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("totalPages")
	private Integer totalPages;

	@JsonProperty("items")
	private List<KinopoiskFilterFilmDto> items;

	public Integer getTotal() {
		return total;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public List<KinopoiskFilterFilmDto> getItems() {
		return items;
	}
}
