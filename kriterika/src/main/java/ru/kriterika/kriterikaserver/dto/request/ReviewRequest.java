package ru.kriterika.kriterikaserver.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewRequest {

	@NotNull(message = "filmId не может быть null")
	private Integer filmId;

	@NotNull(message = "rating не может быть null")
	@Min(value = 1, message = "Рейтинг должен быть от 1 до 10")
	@Max(value = 10, message = "Рейтинг должен быть от 1 до 10")
	private Short rating;

	private String title;

	private String body;

	@Valid
	private List<CriteriaRatingItem> criteriaRatings;

	public ReviewRequest() {
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

	public List<CriteriaRatingItem> getCriteriaRatings() {
		return criteriaRatings;
	}

	public void setCriteriaRatings(List<CriteriaRatingItem> criteriaRatings) {
		this.criteriaRatings = criteriaRatings;
	}

	public static class CriteriaRatingItem {

		@NotNull(message = "criterionId не может быть null")
		private Integer criterionId;

		@NotNull(message = "rating не может быть null")
		@Min(value = 1, message = "Рейтинг должен быть от 1 до 10")
		@Max(value = 10, message = "Рейтинг должен быть от 1 до 10")
		private Short rating;

		public CriteriaRatingItem() {
		}

		public Integer getCriterionId() {
			return criterionId;
		}

		public void setCriterionId(Integer criterionId) {
			this.criterionId = criterionId;
		}

		public Short getRating() {
			return rating;
		}

		public void setRating(Short rating) {
			this.rating = rating;
		}
	}
}
