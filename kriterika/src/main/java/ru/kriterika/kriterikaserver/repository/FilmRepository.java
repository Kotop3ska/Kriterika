package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

	@Query("SELECT f FROM Film f WHERE " +
			"LOWER(f.nameRu) LIKE LOWER(CONCAT('%', REPLACE(REPLACE(:keyword, '-', '%'), ' ', '%'), '%')) " +
			"OR LOWER(f.nameEn) LIKE LOWER(CONCAT('%', REPLACE(REPLACE(:keyword, '-', '%'), ' ', '%'), '%')) " +
			"OR LOWER(f.nameOriginal) LIKE LOWER(CONCAT('%', REPLACE(REPLACE(:keyword, '-', '%'), ' ', '%'), '%'))")
	Page<Film> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	Page<Film> findByYear(Integer year, Pageable pageable);

	@Query("SELECT DISTINCT f FROM Film f JOIN f.filmGenres fg WHERE fg.genreId = :genreId")
	Page<Film> findByGenreId(@Param("genreId") Integer genreId, Pageable pageable);

	@Query("SELECT DISTINCT f FROM Film f JOIN f.filmCountries fc WHERE fc.countryId = :countryId")
	Page<Film> findByCountryId(@Param("countryId") Integer countryId, Pageable pageable);

	@Query("SELECT DISTINCT f FROM Film f " +
			"JOIN f.filmGenres fg " +
			"JOIN f.filmCountries fc " +
			"WHERE fg.genreId = :genreId AND fc.countryId = :countryId")
	Page<Film> findByGenreIdAndCountryId(@Param("genreId") Integer genreId,
										 @Param("countryId") Integer countryId,
										 Pageable pageable);
}
