package ru.kriterika.kriterikaserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class KinopoiskFilmDto {

	@JsonProperty("kinopoiskId")
	private Integer kinopoiskId;

	@JsonProperty("imdbId")
	private String imdbId;

	@JsonProperty("nameRu")
	private String nameRu;

	@JsonProperty("nameEn")
	private String nameEn;

	@JsonProperty("nameOriginal")
	private String nameOriginal;

	@JsonProperty("posterUrl")
	private String posterUrl;

	@JsonProperty("posterUrlPreview")
	private String posterUrlPreview;

	@JsonProperty("coverUrl")
	private String coverUrl;

	@JsonProperty("logoUrl")
	private String logoUrl;

	@JsonProperty("description")
	private String description;

	@JsonProperty("shortDescription")
	private String shortDescription;

	@JsonProperty("year")
	private Integer year;

	@JsonProperty("filmLength")
	private Integer filmLength;

	@JsonProperty("type")
	private String type;

	@JsonProperty("ratingKinopoisk")
	private BigDecimal ratingKinopoisk;

	@JsonProperty("ratingKinopoiskVoteCount")
	private Integer ratingKinopoiskVoteCount;

	@JsonProperty("ratingImdb")
	private BigDecimal ratingImdb;

	@JsonProperty("ratingImdbVoteCount")
	private Integer ratingImdbVoteCount;

	@JsonProperty("ratingAgeLimits")
	private String ratingAgeLimits;

	@JsonProperty("slogan")
	private String slogan;

	@JsonProperty("productionStatus")
	private String productionStatus;

	@JsonProperty("webUrl")
	private String webUrl;

	@JsonProperty("genres")
	private List<GenreDto> genres;

	@JsonProperty("countries")
	private List<CountryDto> countries;

	public Integer getKinopoiskId() {
		return kinopoiskId;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String getNameRu() {
		return nameRu;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public String getPosterUrlPreview() {
		return posterUrlPreview;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getFilmLength() {
		return filmLength;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getRatingKinopoisk() {
		return ratingKinopoisk;
	}

	public Integer getRatingKinopoiskVoteCount() {
		return ratingKinopoiskVoteCount;
	}

	public BigDecimal getRatingImdb() {
		return ratingImdb;
	}

	public Integer getRatingImdbVoteCount() {
		return ratingImdbVoteCount;
	}

	public String getRatingAgeLimits() {
		return ratingAgeLimits;
	}

	public String getSlogan() {
		return slogan;
	}

	public String getProductionStatus() {
		return productionStatus;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public List<GenreDto> getGenres() {
		return genres;
	}

	public List<CountryDto> getCountries() {
		return countries;
	}

	public static class GenreDto {
		@JsonProperty("id")
		private Integer id;
		@JsonProperty("genre")
		private String genre;

		public Integer getId() {
			return id;
		}

		public String getGenre() {
			return genre;
		}
	}

	public static class CountryDto {
		@JsonProperty("id")
		private Integer id;
		@JsonProperty("country")
		private String country;

		public Integer getId() {
			return id;
		}

		public String getCountry() {
			return country;
		}
	}
}
