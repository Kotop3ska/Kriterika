package ru.kriterika.kriterikaserver.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

	private Integer id;
	private Integer userId;
	private String username;
	private Integer filmId;
	private Short rating;
	private String title;
	private String body;
	private LocalDateTime createdAt;
	private List<CriteriaRatingDto> criteriaRatings;

	public ReviewDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public Short getRating() {
		return rating;
	}

	public void setRating(Short rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<CriteriaRatingDto> getCriteriaRatings() {
		return criteriaRatings;
	}

	public void setCriteriaRatings(List<CriteriaRatingDto> criteriaRatings) {
		this.criteriaRatings = criteriaRatings;
	}
}
