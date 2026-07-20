package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.ReviewCriteriaRating;

import java.util.List;

@Repository
public interface ReviewCriteriaRatingRepository extends JpaRepository<ReviewCriteriaRating, Long> {

	List<ReviewCriteriaRating> findByReviewId(Integer reviewId);

	@Modifying
	@Query("DELETE FROM ReviewCriteriaRating r WHERE r.reviewId = :reviewId")
	void deleteByReviewId(@Param("reviewId") Integer reviewId);
}
