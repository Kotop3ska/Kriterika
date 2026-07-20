package ru.kriterika.kriterikaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kriterika.kriterikaserver.client.KinopoiskApiClient;
import ru.kriterika.kriterikaserver.dto.KinopoiskFilmDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskFilterFilmDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskFilterResponseDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskSearchFilmDto;
import ru.kriterika.kriterikaserver.dto.KinopoiskSearchResponseDto;
import ru.kriterika.kriterikaserver.model.Film;
import ru.kriterika.kriterikaserver.model.FilmCountry;
import ru.kriterika.kriterikaserver.model.FilmGenre;
import ru.kriterika.kriterikaserver.repository.CountryRepository;
import ru.kriterika.kriterikaserver.repository.FilmCountryRepository;
import ru.kriterika.kriterikaserver.repository.FilmGenreRepository;
import ru.kriterika.kriterikaserver.repository.FilmRepository;
import ru.kriterika.kriterikaserver.repository.GenreRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class FilmService {

	private static final Logger log = LoggerFactory.getLogger(FilmService.class);

	private final FilmRepository filmRepository;
	private final FilmGenreRepository filmGenreRepository;
	private final FilmCountryRepository filmCountryRepository;
	private final KinopoiskApiClient kinopoiskApiClient;
	private final GenreRepository genreRepository;
	private final CountryRepository countryRepository;

	public FilmService(FilmRepository filmRepository,
	                   FilmGenreRepository filmGenreRepository,
	                   FilmCountryRepository filmCountryRepository,
	                   KinopoiskApiClient kinopoiskApiClient,
	                   GenreRepository genreRepository,
	                   CountryRepository countryRepository) {
		this.filmRepository = filmRepository;
		this.filmGenreRepository = filmGenreRepository;
		this.filmCountryRepository = filmCountryRepository;
		this.kinopoiskApiClient = kinopoiskApiClient;
		this.genreRepository = genreRepository;
		this.countryRepository = countryRepository;
	}

	public Page<Film> searchByKeyword(String keyword, Pageable pageable) {
		Page<Film> localResults = filmRepository.searchByKeyword(keyword, pageable);
		if (!localResults.isEmpty()) {
			return localResults;
		}

		String apiKeyword = keyword.replace("-", " ");
		KinopoiskSearchResponseDto apiResponse = kinopoiskApiClient.searchFilms(apiKeyword, 1);
		if (apiResponse == null || apiResponse.getFilms() == null) {
			return localResults;
		}

		for (KinopoiskSearchFilmDto dto : apiResponse.getFilms()) {
			Optional<Film> existing = filmRepository.findById(dto.getFilmId());
			if (existing.isEmpty()) {
				saveFromSearchDto(dto);
			}
		}

		return filmRepository.searchByKeyword(keyword, pageable);
	}

	public Page<Film> findByYear(Integer year, Pageable pageable) {
		Page<Film> results = filmRepository.findByYear(year, pageable);
		if (!results.isEmpty()) {
			return results;
		}
		KinopoiskSearchResponseDto apiResponse = kinopoiskApiClient.searchFilms(String.valueOf(year), 1);
		if (apiResponse != null && apiResponse.getFilms() != null) {
			for (KinopoiskSearchFilmDto dto : apiResponse.getFilms()) {
				Optional<Film> existing = filmRepository.findById(dto.getFilmId());
				if (existing.isEmpty()) {
					saveFromSearchDto(dto);
				}
			}
		}
		return filmRepository.findByYear(year, pageable);
	}

	public Page<Film> findByGenreId(Integer genreId, Pageable pageable) {
		Page<Film> results = filmRepository.findByGenreId(genreId, pageable);
		if (results.getTotalElements() >= pageable.getPageSize()) {
			return results;
		}
		for (int p = 1; p <= 5; p++) {
			KinopoiskFilterResponseDto apiResponse = kinopoiskApiClient.getFilmsByFilter(genreId, null, p);
			if (apiResponse == null || apiResponse.getItems() == null || apiResponse.getItems().isEmpty()) {
				break;
			}
			for (KinopoiskFilterFilmDto dto : apiResponse.getItems()) {
				if (filmRepository.findById(dto.getKinopoiskId()).isEmpty()) {
					saveFromFilterDto(dto);
				}
			}
			if (p < 5) {
				try { Thread.sleep(200); } catch (InterruptedException ignored) {}
			}
		}
		return filmRepository.findByGenreId(genreId, pageable);
	}

	public Page<Film> findByCountryId(Integer countryId, Pageable pageable) {
		Page<Film> results = filmRepository.findByCountryId(countryId, pageable);
		if (results.getTotalElements() >= pageable.getPageSize()) {
			return results;
		}
		for (int p = 1; p <= 5; p++) {
			KinopoiskFilterResponseDto apiResponse = kinopoiskApiClient.getFilmsByFilter(null, countryId, p);
			if (apiResponse == null || apiResponse.getItems() == null || apiResponse.getItems().isEmpty()) {
				break;
			}
			for (KinopoiskFilterFilmDto dto : apiResponse.getItems()) {
				if (filmRepository.findById(dto.getKinopoiskId()).isEmpty()) {
					saveFromFilterDto(dto);
				}
			}
			if (p < 5) {
				try { Thread.sleep(200); } catch (InterruptedException ignored) {}
			}
		}
		return filmRepository.findByCountryId(countryId, pageable);
	}

	private Film saveFromFilterDto(KinopoiskFilterFilmDto dto) {
		Film film = new Film();
		film.setKinopoiskId(dto.getKinopoiskId());
		film.setNameRu(dto.getNameRu());
		film.setNameEn(dto.getNameEn());
		film.setNameOriginal(dto.getNameOriginal());
		film.setPosterUrl(dto.getPosterUrl());
		film.setPosterUrlPreview(dto.getPosterUrlPreview());
		film.setType(dto.getType());
		film.setYear(dto.getYear());
		film.setRatingKinopoisk(dto.getRatingKinopoisk() != null
				? java.math.BigDecimal.valueOf(dto.getRatingKinopoisk()) : null);
		film.setLastSync(LocalDateTime.now());

		Film saved = filmRepository.save(film);

		if (dto.getGenres() != null) {
			for (KinopoiskFilmDto.GenreDto genreDto : dto.getGenres()) {
				Integer genreId = genreDto.getId();
				if (genreId == null && genreDto.getGenre() != null) {
					genreId = genreRepository.findByName(genreDto.getGenre())
							.map(g -> g.getId()).orElse(null);
				}
				if (genreId != null) {
					filmGenreRepository.save(new FilmGenre(saved.getKinopoiskId(), genreId));
				}
			}
		}
		if (dto.getCountries() != null) {
			for (KinopoiskFilmDto.CountryDto countryDto : dto.getCountries()) {
				Integer countryId = countryDto.getId();
				if (countryId == null && countryDto.getCountry() != null) {
					countryId = countryRepository.findByName(countryDto.getCountry())
							.map(c -> c.getId()).orElse(null);
				}
				if (countryId != null) {
					filmCountryRepository.save(new FilmCountry(saved.getKinopoiskId(), countryId));
				}
			}
		}

		log.info("Сохранён фильм из фильтра: {} (id={})", dto.getNameRu(), dto.getKinopoiskId());
		return saved;
	}

	public Optional<Film> findById(Integer id) {
		Optional<Film> localFilm = filmRepository.findById(id);
		if (localFilm.isPresent()) {
			Film film = localFilm.get();
			if (film.getDescription() != null) {
				return localFilm;
			}
		}

		KinopoiskFilmDto dto = kinopoiskApiClient.getFilmById(id);
		if (dto == null) {
			return localFilm;
		}

		Film film = saveFromFullDto(dto);
		return Optional.of(film);
	}

	private Film saveFromSearchDto(KinopoiskSearchFilmDto dto) {
		Film film = new Film();
		film.setKinopoiskId(dto.getFilmId());
		film.setNameRu(dto.getNameRu());
		film.setNameEn(dto.getNameEn());
		film.setType(dto.getType());
		film.setPosterUrl(dto.getPosterUrl());
		film.setPosterUrlPreview(dto.getPosterUrlPreview());
		film.setLastSync(LocalDateTime.now());

		try {
			if (dto.getYear() != null) {
				film.setYear(Integer.parseInt(dto.getYear()));
			}
		} catch (NumberFormatException ignored) {
		}

		Film saved = filmRepository.save(film);

		if (dto.getGenres() != null) {
			for (KinopoiskFilmDto.GenreDto genreDto : dto.getGenres()) {
				if (genreDto.getId() != null) {
					FilmGenre fg = new FilmGenre(saved.getKinopoiskId(), genreDto.getId());
					filmGenreRepository.save(fg);
				}
			}
		}

		if (dto.getCountries() != null) {
			for (KinopoiskFilmDto.CountryDto countryDto : dto.getCountries()) {
				if (countryDto.getId() != null) {
					FilmCountry fc = new FilmCountry(saved.getKinopoiskId(), countryDto.getId());
					filmCountryRepository.save(fc);
				}
			}
		}

		log.info("Сохранён фильм из поиска: {} (id={})", dto.getNameRu(), dto.getFilmId());
		return saved;
	}

	private Film saveFromFullDto(KinopoiskFilmDto dto) {
		Film film = new Film();
		film.setKinopoiskId(dto.getKinopoiskId());
		film.setImdbId(dto.getImdbId());
		film.setNameRu(dto.getNameRu());
		film.setNameEn(dto.getNameEn());
		film.setNameOriginal(dto.getNameOriginal());
		film.setPosterUrl(dto.getPosterUrl());
		film.setPosterUrlPreview(dto.getPosterUrlPreview());
		film.setCoverUrl(dto.getCoverUrl());
		film.setLogoUrl(dto.getLogoUrl());
		film.setDescription(dto.getDescription());
		film.setShortDescription(dto.getShortDescription());
		film.setYear(dto.getYear());
		film.setFilmLength(dto.getFilmLength());
		film.setType(dto.getType());
		film.setRatingKinopoisk(dto.getRatingKinopoisk());
		film.setRatingKinopoiskVoteCount(dto.getRatingKinopoiskVoteCount());
		film.setRatingImdb(dto.getRatingImdb());
		film.setRatingImdbVoteCount(dto.getRatingImdbVoteCount());
		film.setRatingAgeLimits(dto.getRatingAgeLimits());
		film.setSlogan(dto.getSlogan());
		film.setProductionStatus(dto.getProductionStatus());
		film.setWebUrl(dto.getWebUrl());
		film.setLastSync(LocalDateTime.now());

		Film saved = filmRepository.save(film);

		if (dto.getGenres() != null) {
			for (KinopoiskFilmDto.GenreDto genreDto : dto.getGenres()) {
				if (genreDto.getId() != null) {
					FilmGenre fg = new FilmGenre(saved.getKinopoiskId(), genreDto.getId());
					filmGenreRepository.save(fg);
				}
			}
		}

		if (dto.getCountries() != null) {
			for (KinopoiskFilmDto.CountryDto countryDto : dto.getCountries()) {
				if (countryDto.getId() != null) {
					FilmCountry fc = new FilmCountry(saved.getKinopoiskId(), countryDto.getId());
					filmCountryRepository.save(fc);
				}
			}
		}

		log.info("Загружен фильм из API: {} (id={})", dto.getNameRu(), dto.getKinopoiskId());
		return saved;
	}

	public Film save(Film film) {
		return filmRepository.save(film);
	}
}
