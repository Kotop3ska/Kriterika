package ru.kriterika.kriterikaserver.dto.response;

import java.math.BigDecimal;

public class FilmSearchDto {

	private Integer kinopoiskId;
	private String nameRu;
	private String nameEn;
	private String posterUrl;
	private String posterUrlPreview;
	private Integer year;
	private String type;
	private BigDecimal ratingKinopoisk;

	public FilmSearchDto() {
	}

	public Integer getKinopoiskId() {
		return kinopoiskId;
	}

	public void setKinopoiskId(Integer kinopoiskId) {
		this.kinopoiskId = kinopoiskId;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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
}
