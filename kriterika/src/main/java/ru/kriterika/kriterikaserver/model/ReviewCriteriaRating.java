package ru.kriterika.kriterikaserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_criteria_ratings")
@IdClass(ReviewCriteriaRating.class)
public class ReviewCriteriaRating {

	@Column(name = "review_id", nullable = false)
	@Id
	private Integer reviewId;

	@Column(name = "criterion_id", nullable = false)
	@Id
	private Integer criterionId;

	@Column(nullable = false)
	private Short rating;

	public ReviewCriteriaRating() {}

	public ReviewCriteriaRating(Integer reviewId, Integer criterionId, Short rating) {
		this.reviewId = reviewId;
		this.criterionId = criterionId;
		this.rating = rating;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
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
