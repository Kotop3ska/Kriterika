package ru.kriterika.kriterikaserver.dto.response;

public class CriteriaRatingDto {

	private Integer criterionId;
	private String criterionName;
	private Short rating;

	public CriteriaRatingDto() {
	}

	public CriteriaRatingDto(Integer criterionId, String criterionName, Short rating) {
		this.criterionId = criterionId;
		this.criterionName = criterionName;
		this.rating = rating;
	}

	public Integer getCriterionId() {
		return criterionId;
	}

	public void setCriterionId(Integer criterionId) {
		this.criterionId = criterionId;
	}

	public String getCriterionName() {
		return criterionName;
	}

	public void setCriterionName(String criterionName) {
		this.criterionName = criterionName;
	}

	public Short getRating() {
		return rating;
	}

	public void setRating(Short rating) {
		this.rating = rating;
	}
}
