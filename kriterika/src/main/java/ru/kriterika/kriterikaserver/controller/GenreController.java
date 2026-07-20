package ru.kriterika.kriterikaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kriterika.kriterikaserver.model.Genre;
import ru.kriterika.kriterikaserver.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	@GetMapping
	public ResponseEntity<List<Genre>> getAll() {
		return ResponseEntity.ok(genreService.findAll());
	}
}
