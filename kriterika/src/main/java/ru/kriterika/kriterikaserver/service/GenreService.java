package ru.kriterika.kriterikaserver.service;

import org.springframework.stereotype.Service;
import ru.kriterika.kriterikaserver.model.Genre;
import ru.kriterika.kriterikaserver.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

	private final GenreRepository genreRepository;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public List<Genre> findAll() {
		return genreRepository.findAll();
	}
}
