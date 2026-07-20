package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByFilmId(Integer filmId);

	List<Review> findByUserId(Integer userId);

	Optional<Review> findByUserIdAndFilmId(Integer userId, Integer filmId);

	@Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.filmId = :filmId")
	Double getAverageRatingByFilmId(@Param("filmId") Integer filmId);

	@Query("SELECT COUNT(r) FROM Review r WHERE r.filmId = :filmId")
	Long getCountByFilmId(@Param("filmId") Integer filmId);
}
