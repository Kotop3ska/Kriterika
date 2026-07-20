package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

	Optional<Genre> findByName(String name);
}
