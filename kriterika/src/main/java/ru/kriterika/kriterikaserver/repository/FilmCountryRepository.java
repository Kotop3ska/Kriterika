package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.FilmCountry;

import java.util.List;

@Repository
public interface FilmCountryRepository extends JpaRepository<FilmCountry, Long> {

	List<FilmCountry> findByFilmId(Integer filmId);

	List<FilmCountry> findByCountryId(Integer countryId);
}
