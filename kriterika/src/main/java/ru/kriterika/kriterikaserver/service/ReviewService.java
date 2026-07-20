package ru.kriterika.kriterikaserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kriterika.kriterikaserver.model.Review;
import ru.kriterika.kriterikaserver.model.ReviewCriteriaRating;
import ru.kriterika.kriterikaserver.repository.ReviewCriteriaRatingRepository;
import ru.kriterika.kriterikaserver.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewCriteriaRatingRepository criteriaRatingRepository;

	public ReviewService(ReviewRepository reviewRepository,
	                     ReviewCriteriaRatingRepository criteriaRatingRepository) {
		this.reviewRepository = reviewRepository;
		this.criteriaRatingRepository = criteriaRatingRepository;
	}

	public Review createReview(Review review) {
		Optional<Review> existing = reviewRepository.findByUserIdAndFilmId(
				review.getUserId(), review.getFilmId());
		if (existing.isPresent()) {
			throw new RuntimeException("Вы уже оставили отзыв на этот фильм");
		}
		return reviewRepository.save(review);
	}

	public Review updateReview(Integer reviewId, Integer userId, Short rating, String title, String body) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new RuntimeException("Отзыв не найден"));
		if (!review.getUserId().equals(userId)) {
			throw new SecurityException("Нет прав на редактирование этого отзыва");
		}
		if (rating != null) {
			review.setRating(rating);
		}
		if (title != null) {
			review.setTitle(title);
		}
		if (body != null) {
			review.setBody(body);
		}
		return reviewRepository.save(review);
	}

	public void deleteReview(Integer reviewId, Integer userId) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new RuntimeException("Отзыв не найден"));
		if (!review.getUserId().equals(userId)) {
			throw new SecurityException("Нет прав на удаление этого отзыва");
		}
		criteriaRatingRepository.deleteByReviewId(reviewId);
		reviewRepository.delete(review);
	}

	public void updateCriteriaRatings(Integer reviewId, Integer userId,
	                                  List<ReviewCriteriaRating> ratings) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new RuntimeException("Отзыв не найден"));
		if (!review.getUserId().equals(userId)) {
			throw new SecurityException("Нет прав на редактирование этого отзыва");
		}
		criteriaRatingRepository.deleteByReviewId(reviewId);
		for (ReviewCriteriaRating rcr : ratings) {
			rcr.setReviewId(reviewId);
			criteriaRatingRepository.save(rcr);
		}
	}

	public List<Review> getReviewsByFilmId(Integer filmId) {
		return reviewRepository.findByFilmId(filmId);
	}

	public List<Review> getReviewsByUserId(Integer userId) {
		return reviewRepository.findByUserId(userId);
	}

	public Optional<Review> getReviewByUserAndFilm(Integer userId, Integer filmId) {
		return reviewRepository.findByUserIdAndFilmId(userId, filmId);
	}

	public Optional<Review> findById(Integer reviewId) {
		return reviewRepository.findById(reviewId);
	}

	public void addCriteriaRating(Integer reviewId, Integer criterionId, Short rating) {
		ReviewCriteriaRating rcr = new ReviewCriteriaRating(reviewId, criterionId, rating);
		criteriaRatingRepository.save(rcr);
	}

	public List<ReviewCriteriaRating> getCriteriaRatings(Integer reviewId) {
		return criteriaRatingRepository.findByReviewId(reviewId);
	}

	public Double getAverageRating(Integer filmId) {
		return reviewRepository.getAverageRatingByFilmId(filmId);
	}

	public Long getReviewCount(Integer filmId) {
		return reviewRepository.getCountByFilmId(filmId);
	}
}
