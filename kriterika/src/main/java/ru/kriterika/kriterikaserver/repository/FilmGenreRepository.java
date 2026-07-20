package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.FilmGenre;

import java.util.List;

@Repository
public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {

	List<FilmGenre> findByFilmId(Integer filmId);

	List<FilmGenre> findByGenreId(Integer genreId);
}
