package ru.kriterika.kriterikaserver.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kriterika.kriterikaserver.dto.response.FilmDto;
import ru.kriterika.kriterikaserver.dto.response.FilmSearchDto;
import ru.kriterika.kriterikaserver.dto.response.ReviewDto;
import ru.kriterika.kriterikaserver.model.*;
import ru.kriterika.kriterikaserver.repository.CriterionRepository;
import ru.kriterika.kriterikaserver.service.FilmService;
import ru.kriterika.kriterikaserver.service.ReviewService;
import ru.kriterika.kriterikaserver.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/films")
public class FilmController {

	private final FilmService filmService;
	private final ReviewService reviewService;
	private final UserService userService;
	private final CriterionRepository criterionRepository;

	public FilmController(FilmService filmService, ReviewService reviewService,
	                      UserService userService, CriterionRepository criterionRepository) {
		this.filmService = filmService;
		this.reviewService = reviewService;
		this.userService = userService;
		this.criterionRepository = criterionRepository;
	}

	@GetMapping
	public ResponseEntity<Page<FilmSearchDto>> search(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer genreId,
			@RequestParam(required = false) Integer countryId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "kinopoiskId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Film> films;
		if (keyword != null) {
			films = filmService.searchByKeyword(keyword, pageable);
		} else if (year != null) {
			films = filmService.findByYear(year, pageable);
		} else if (genreId != null && countryId != null) {
			films = filmService.findByGenreIdAndCountryId(genreId, countryId, pageable);
		} else if (genreId != null) {
			films = filmService.findByGenreId(genreId, pageable);
		} else if (countryId != null) {
			films = filmService.findByCountryId(countryId, pageable);
		} else {
			films = filmService.searchByKeyword("", pageable);
		}

		return ResponseEntity.ok(films.map(this::toSearchDto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return filmService.findById(id)
				.map(film -> {
					Double avgRating = reviewService.getAverageRating(id);
					Long reviewCount = reviewService.getReviewCount(id);
					return ResponseEntity.ok((Object) Map.of(
							"film", toFullDto(film),
							"averageRating", avgRating,
							"reviewCount", reviewCount
					));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/reviews")
	public ResponseEntity<?> getReviews(@PathVariable Integer id) {
		if (filmService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<ReviewDto> reviews = reviewService.getReviewsByFilmId(id).stream()
				.map(this::toReviewDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(reviews);
	}

	private FilmSearchDto toSearchDto(Film film) {
		FilmSearchDto dto = new FilmSearchDto();
		dto.setKinopoiskId(film.getKinopoiskId());
		dto.setNameRu(film.getNameRu());
		dto.setNameEn(film.getNameEn());
		dto.setPosterUrl(film.getPosterUrl());
		dto.setPosterUrlPreview(film.getPosterUrlPreview());
		dto.setYear(film.getYear());
		dto.setType(film.getType());
		dto.setRatingKinopoisk(film.getRatingKinopoisk());
		return dto;
	}

	private FilmDto toFullDto(Film film) {
		FilmDto dto = new FilmDto();
		dto.setKinopoiskId(film.getKinopoiskId());
		dto.setImdbId(film.getImdbId());
		dto.setNameRu(film.getNameRu());
		dto.setNameEn(film.getNameEn());
		dto.setNameOriginal(film.getNameOriginal());
		dto.setPosterUrl(film.getPosterUrl());
		dto.setPosterUrlPreview(film.getPosterUrlPreview());
		dto.setCoverUrl(film.getCoverUrl());
		dto.setLogoUrl(film.getLogoUrl());
		dto.setDescription(film.getDescription());
		dto.setShortDescription(film.getShortDescription());
		dto.setYear(film.getYear());
		dto.setFilmLength(film.getFilmLength());
		dto.setType(film.getType());
		dto.setRatingKinopoisk(film.getRatingKinopoisk());
		dto.setRatingKinopoiskVoteCount(film.getRatingKinopoiskVoteCount());
		dto.setRatingImdb(film.getRatingImdb());
		dto.setRatingImdbVoteCount(film.getRatingImdbVoteCount());
		dto.setRatingAgeLimits(film.getRatingAgeLimits());
		dto.setSlogan(film.getSlogan());
		dto.setProductionStatus(film.getProductionStatus());
		dto.setWebUrl(film.getWebUrl());

		if (film.getFilmGenres() != null) {
			List<FilmDto.GenreDto> genres = film.getFilmGenres().stream()
					.map(fg -> new FilmDto.GenreDto(fg.getGenreId(), null))
					.collect(Collectors.toList());
			dto.setGenres(genres);
		}
		if (film.getFilmCountries() != null) {
			List<FilmDto.CountryDto> countries = film.getFilmCountries().stream()
					.map(fc -> new FilmDto.CountryDto(fc.getCountryId(), null))
					.collect(Collectors.toList());
			dto.setCountries(countries);
		}
		return dto;
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
						return new ru.kriterika.kriterikaserver.dto.response.CriteriaRatingDto(
								cr.getCriterionId(), name, cr.getRating());
					})
					.collect(Collectors.toList()));
		}

		return dto;
	}
}
