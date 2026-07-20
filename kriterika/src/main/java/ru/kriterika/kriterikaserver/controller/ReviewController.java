package ru.kriterika.kriterikaserver.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kriterika.kriterikaserver.dto.request.ReviewRequest;
import ru.kriterika.kriterikaserver.dto.request.UpdateReviewRequest;
import ru.kriterika.kriterikaserver.dto.response.CriteriaRatingDto;
import ru.kriterika.kriterikaserver.dto.response.ReviewDto;
import ru.kriterika.kriterikaserver.model.Criterion;
import ru.kriterika.kriterikaserver.model.Review;
import ru.kriterika.kriterikaserver.model.ReviewCriteriaRating;
import ru.kriterika.kriterikaserver.model.User;
import ru.kriterika.kriterikaserver.repository.CriterionRepository;
import ru.kriterika.kriterikaserver.security.UserPrincipal;
import ru.kriterika.kriterikaserver.service.ReviewService;
import ru.kriterika.kriterikaserver.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final UserService userService;
	private final CriterionRepository criterionRepository;

	public ReviewController(ReviewService reviewService, UserService userService,
	                        CriterionRepository criterionRepository) {
		this.reviewService = reviewService;
		this.userService = userService;
		this.criterionRepository = criterionRepository;
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ReviewRequest request) {
		Integer userId = getCurrentUserId();
		Review review = new Review(userId, request.getFilmId(), request.getRating(),
				request.getTitle(), request.getBody());
		Review saved = reviewService.createReview(review);

		if (request.getCriteriaRatings() != null) {
			for (ReviewRequest.CriteriaRatingItem cr : request.getCriteriaRatings()) {
				reviewService.addCriteriaRating(saved.getId(), cr.getCriterionId(), cr.getRating());
			}
		}

		return ResponseEntity.ok(Map.of("id", saved.getId()));
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<?> update(@PathVariable Integer reviewId,
	                                @Valid @RequestBody UpdateReviewRequest request) {
		Integer userId = getCurrentUserId();
		Review updated = reviewService.updateReview(reviewId, userId,
				request.getRating(), request.getTitle(), request.getBody());

		if (request.getCriteriaRatings() != null) {
			List<ReviewCriteriaRating> ratings = request.getCriteriaRatings().stream()
					.map(cr -> new ReviewCriteriaRating(reviewId, cr.getCriterionId(), cr.getRating()))
					.collect(Collectors.toList());
			reviewService.updateCriteriaRatings(reviewId, userId, ratings);
		}

		return ResponseEntity.ok(toReviewDto(updated));
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> delete(@PathVariable Integer reviewId) {
		Integer userId = getCurrentUserId();
		reviewService.deleteReview(reviewId, userId);
		return ResponseEntity.ok(Map.of("message", "Отзыв удалён"));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ReviewDto>> getByUserId(@PathVariable Integer userId) {
		List<ReviewDto> reviews = reviewService.getReviewsByUserId(userId).stream()
				.map(this::toReviewDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(reviews);
	}

	@GetMapping("/{reviewId}/criteria")
	public ResponseEntity<?> getCriteriaRatings(@PathVariable Integer reviewId) {
		List<ReviewCriteriaRating> ratings = reviewService.getCriteriaRatings(reviewId);
		List<CriteriaRatingDto> dtos = ratings.stream()
				.map(r -> {
					String name = criterionRepository.findById(r.getCriterionId())
							.map(Criterion::getName).orElse(null);
					return new CriteriaRatingDto(r.getCriterionId(), name, r.getRating());
				})
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}

	private ReviewDto toReviewDto(Review review) {
		ReviewDto dto = new ReviewDto();
		dto.setId(review.getId());
		dto.setUserId(review.getUserId());
		dto.setFilmId(review.getFilmId());
		dto.setRating(review.getRating());
		dto.setTitle(review.getTitle());
		dto.setBody(review.getBody());
		dto.setCreatedAt(review.getCreatedAt());

		User user = userService.findById(review.getUserId()).orElse(null);
		if (user != null) {
			dto.setUsername(user.getUsername());
		}

		List<ReviewCriteriaRating> criteriaRatings = reviewService.getCriteriaRatings(review.getId());
		if (!criteriaRatings.isEmpty()) {
			dto.setCriteriaRatings(criteriaRatings.stream()
					.map(cr -> {
						String name = criterionRepository.findById(cr.getCriterionId())
								.map(Criterion::getName).orElse(null);
						return new CriteriaRatingDto(cr.getCriterionId(), name, cr.getRating());
					})
					.collect(Collectors.toList()));
		}

		return dto;
	}

	private Integer getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
		return principal.getUserId();
	}
}
