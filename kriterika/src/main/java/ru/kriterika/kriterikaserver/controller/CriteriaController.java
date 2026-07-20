package ru.kriterika.kriterikaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kriterika.kriterikaserver.model.Criterion;
import ru.kriterika.kriterikaserver.service.CriteriaService;

import java.util.List;

@RestController
@RequestMapping("/api/criteria")
public class CriteriaController {

	private final CriteriaService criteriaService;

	public CriteriaController(CriteriaService criteriaService) {
		this.criteriaService = criteriaService;
	}

	@GetMapping
	public ResponseEntity<List<Criterion>> getAll() {
		return ResponseEntity.ok(criteriaService.findAll());
	}
}
