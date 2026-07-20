package ru.kriterika.kriterikaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kriterika.kriterikaserver.model.Criterion;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion, Integer> {
}
