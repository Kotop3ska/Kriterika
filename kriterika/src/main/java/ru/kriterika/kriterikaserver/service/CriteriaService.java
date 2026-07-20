package ru.kriterika.kriterikaserver.service;

import org.springframework.stereotype.Service;
import ru.kriterika.kriterikaserver.model.Criterion;
import ru.kriterika.kriterikaserver.repository.CriterionRepository;

import java.util.List;

@Service
public class CriteriaService {

	private final CriterionRepository criterionRepository;

	public CriteriaService(CriterionRepository criterionRepository) {
		this.criterionRepository = criterionRepository;
	}

	public List<Criterion> findAll() {
		return criterionRepository.findAll();
	}
}
