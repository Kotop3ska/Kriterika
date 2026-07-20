package ru.kriterika.kriterikaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kriterika.kriterikaserver.model.Country;
import ru.kriterika.kriterikaserver.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping
	public ResponseEntity<List<Country>> getAll() {
		return ResponseEntity.ok(countryService.findAll());
	}
}
