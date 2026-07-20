package ru.kriterika.kriterikaserver.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public class UpdateReviewRequest {

	@Min(value = 1, message = "Рейтинг должен быть от 1 до 10")
	@Max(value = 10, message = "Рейтинг должен быть от 1 до 10")
	private Short rating;

	private String title;

	private String body;

	private List<ReviewRequest.CriteriaRatingItem> criteriaRatings;

	public UpdateReviewRequest() {
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

	public List<ReviewRequest.CriteriaRatingItem> getCriteriaRatings() {
		return criteriaRatings;
	}

	public void setCriteriaRatings(List<ReviewRequest.CriteriaRatingItem> criteriaRatings) {
		this.criteriaRatings = criteriaRatings;
	}
}
