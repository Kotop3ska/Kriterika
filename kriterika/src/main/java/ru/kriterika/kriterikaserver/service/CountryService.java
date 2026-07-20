package ru.kriterika.kriterikaserver.service;

import org.springframework.stereotype.Service;
import ru.kriterika.kriterikaserver.model.Country;
import ru.kriterika.kriterikaserver.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {

	private final CountryRepository countryRepository;

	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public List<Country> findAll() {
		return countryRepository.findAll();
	}
}
