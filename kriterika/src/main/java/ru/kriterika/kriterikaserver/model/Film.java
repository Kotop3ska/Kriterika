package ru.kriterika.kriterikaserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "films")
public class Film {

	@Id
	@Column(name = "kinopoisk_id")
	private Integer kinopoiskId;

	@Column(name = "imdb_id")
	private String imdbId;

	@Column(name = "name_ru")
	private String nameRu;

	@Column(name = "name_en")
	private String nameEn;

	@Column(name = "name_original")
	private String nameOriginal;

	@Column(name = "poster_url")
	private String posterUrl;

	@Column(name = "poster_url_preview")
	private String posterUrlPreview;

	@Column(name = "cover_url")
	private String coverUrl;

	@Column(name = "logo_url")
	private String logoUrl;

	private String description;

	@Column(name = "short_description")
	private String shortDescription;

	private Integer year;

	@Column(name = "film_length")
	private Integer filmLength;

	@Column(nullable = false)
	private String type;

	@Column(name = "rating_kinopoisk", precision = 3, scale = 1)
	private BigDecimal ratingKinopoisk;

	@Column(name = "rating_kinopoisk_vote_count")
	private Integer ratingKinopoiskVoteCount;

	@Column(name = "rating_imdb", precision = 3, scale = 1)
	private BigDecimal ratingImdb;

	@Column(name = "rating_imdb_vote_count")
	private Integer ratingImdbVoteCount;

	@Column(name = "rating_age_limits")
	private String ratingAgeLimits;

	private String slogan;

	@Column(name = "production_status")
	private String productionStatus;

	@Column(name = "web_url")
	private String webUrl;

	@Column(name = "last_sync", nullable = false)
	private LocalDateTime lastSync;

	@OneToMany
	@JoinColumn(name = "film_id", insertable = false, updatable = false)
	private List<FilmGenre> filmGenres;

	@OneToMany
	@JoinColumn(name = "film_id", insertable = false, updatable = false)
	private List<FilmCountry> filmCountries;

	public Film() {}

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

	public LocalDateTime getLastSync() {
		return lastSync;
	}

	public void setLastSync(LocalDateTime lastSync) {
		this.lastSync = lastSync;
	}

	public List<FilmGenre> getFilmGenres() {
		return filmGenres;
	}

	public void setFilmGenres(List<FilmGenre> filmGenres) {
		this.filmGenres = filmGenres;
	}

	public List<FilmCountry> getFilmCountries() {
		return filmCountries;
	}

	public void setFilmCountries(List<FilmCountry> filmCountries) {
		this.filmCountries = filmCountries;
	}
}
