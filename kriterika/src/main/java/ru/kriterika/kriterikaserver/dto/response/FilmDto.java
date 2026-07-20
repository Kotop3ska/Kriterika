package ru.kriterika.kriterikaserver.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class FilmDto {

	private Integer kinopoiskId;
	private String imdbId;
	private String nameRu;
	private String nameEn;
	private String nameOriginal;
	private String posterUrl;
	private String posterUrlPreview;
	private String coverUrl;
	private String logoUrl;
	private String description;
	private String shortDescription;
	private Integer year;
	private Integer filmLength;
	private String type;
	private BigDecimal ratingKinopoisk;
	private Integer ratingKinopoiskVoteCount;
	private BigDecimal ratingImdb;
	private Integer ratingImdbVoteCount;
	private String ratingAgeLimits;
	private String slogan;
	private String productionStatus;
	private String webUrl;
	private List<GenreDto> genres;
	private List<CountryDto> countries;

	public FilmDto() {
	}

	public Integer getKinopoiskId() {
		return kinopoiskId;
	}

	public void setKinopoiskId(Integer kinopoiskId) {
		this.kinopoiskId = kinopoiskId;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public void setNameOriginal(String nameOriginal) {
		this.nameOriginal = nameOriginal;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getPosterUrlPreview() {
		return posterUrlPreview;
	}

	public void setPosterUrlPreview(String posterUrlPreview) {
		this.posterUrlPreview = posterUrlPreview;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getFilmLength() {
		return filmLength;
	}

	public void setFilmLength(Integer filmLength) {
		this.filmLength = filmLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getRatingKinopoisk() {
		return ratingKinopoisk;
	}

	public void setRatingKinopoisk(BigDecimal ratingKinopoisk) {
		this.ratingKinopoisk = ratingKinopoisk;
	}

	public Integer getRatingKinopoiskVoteCount() {
		return ratingKinopoiskVoteCount;
	}

	public void setRatingKinopoiskVoteCount(Integer ratingKinopoiskVoteCount) {
		this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
	}

	public BigDecimal getRatingImdb() {
		return ratingImdb;
	}

	public void setRatingImdb(BigDecimal ratingImdb) {
		this.ratingImdb = ratingImdb;
	}

	public Integer getRatingImdbVoteCount() {
		return ratingImdbVoteCount;
	}

	public void setRatingImdbVoteCount(Integer ratingImdbVoteCount) {
		this.ratingImdbVoteCount = ratingImdbVoteCount;
	}

	public String getRatingAgeLimits() {
		return ratingAgeLimits;
	}

	public void setRatingAgeLimits(String ratingAgeLimits) {
		this.ratingAgeLimits = ratingAgeLimits;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getProductionStatus() {
		return productionStatus;
	}

	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public List<GenreDto> getGenres() {
		return genres;
	}

	public void setGenres(List<GenreDto> genres) {
		this.genres = genres;
	}

	public List<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}

	public static class GenreDto {
		private Integer id;
		private String name;

		public GenreDto() {
		}

		public GenreDto(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class CountryDto {
		private Integer id;
		private String name;

		public CountryDto() {
		}

		public CountryDto(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
