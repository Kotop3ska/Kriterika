-- --------------------------------------------
-- Таблица пользователей
-- Хранит данные авторизации пользователей сервиса
-- --------------------------------------------
CREATE TABLE users (
    id SERIAL,
    email VARCHAR(255),
    username VARCHAR(100),
    password_hash VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

-- --------------------------------------------
-- Справочник жанров
-- ID совпадают с ID из API Кинопоиска (/api/v2.2/films/filters)
-- --------------------------------------------
CREATE TABLE genres (
    id INTEGER,
    name VARCHAR(100)
);

-- --------------------------------------------
-- Справочник стран
-- ID совпадают с ID из API Кинопоиска (/api/v2.2/films/filters)
-- --------------------------------------------
CREATE TABLE countries (
    id INTEGER,
    name VARCHAR(200)
);

-- --------------------------------------------
-- Таблица фильмов
-- Все данные берутся из API Кинопоиска и кешируются локально
-- --------------------------------------------
CREATE TABLE films (
    kinopoisk_id INTEGER,
    imdb_id VARCHAR(20),
    name_ru VARCHAR(500),
    name_en VARCHAR(500),
    name_original VARCHAR(500),
    poster_url TEXT,
    poster_url_preview TEXT,
    cover_url TEXT,
    logo_url TEXT,
    description TEXT,
    short_description TEXT,
    year INTEGER,
    film_length INTEGER,
    type VARCHAR(20),
    rating_kinopoisk NUMERIC(3,1),
    rating_kinopoisk_vote_count INTEGER,
    rating_imdb NUMERIC(3,1),
    rating_imdb_vote_count INTEGER,
    rating_age_limits VARCHAR(10),
    slogan TEXT,
    production_status VARCHAR(20),
    web_url TEXT,
    last_sync TIMESTAMP
);

-- --------------------------------------------
-- Связь фильмов и жанров (M:N)
-- --------------------------------------------
CREATE TABLE film_genres (
    film_id INTEGER,
    genre_id INTEGER
);

-- --------------------------------------------
-- Связь фильмов и стран (M:N)
-- --------------------------------------------
CREATE TABLE film_countries (
    film_id INTEGER,
    country_id INTEGER
);

-- --------------------------------------------
-- Отзывы пользователей
-- Один пользователь может оставить только один отзыв на фильм
-- --------------------------------------------
CREATE TABLE reviews (
    id SERIAL,
    user_id INTEGER,
    film_id INTEGER,
    rating SMALLINT,
    title VARCHAR(255),
    body TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- --------------------------------------------
-- Справочник критериев оценки
-- Каждый критерий — отдельная ось на розе ветров
-- --------------------------------------------
CREATE TABLE criteria (
    id SERIAL,
    name VARCHAR(100)
);

-- --------------------------------------------
-- Оценки по критериям для каждого отзыва
-- Связывает отзыв с конкретными оценками по критериям
-- --------------------------------------------
CREATE TABLE review_criteria_ratings (
    review_id INTEGER,
    criterion_id INTEGER,
    rating SMALLINT
);


-- ============================================
-- Ограничения (PRIMARY KEY)
-- ============================================

ALTER TABLE users ADD CONSTRAINT pk_users PRIMARY KEY (id);
ALTER TABLE genres ADD CONSTRAINT pk_genres PRIMARY KEY (id);
ALTER TABLE countries ADD CONSTRAINT pk_countries PRIMARY KEY (id);
ALTER TABLE films ADD CONSTRAINT pk_films PRIMARY KEY (kinopoisk_id);
ALTER TABLE film_genres ADD CONSTRAINT pk_film_genres PRIMARY KEY (film_id, genre_id);
ALTER TABLE film_countries ADD CONSTRAINT pk_film_countries PRIMARY KEY (film_id, country_id);
ALTER TABLE reviews ADD CONSTRAINT pk_reviews PRIMARY KEY (id);
ALTER TABLE criteria ADD CONSTRAINT pk_criteria PRIMARY KEY (id);
ALTER TABLE review_criteria_ratings ADD CONSTRAINT pk_review_criteria_ratings PRIMARY KEY (review_id, criterion_id);


-- ============================================
-- Ограничения (UNIQUE)
-- ============================================

-- Уникальность email и username пользователей
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT uq_users_username UNIQUE (username);

-- Уникальность названий жанров и стран
ALTER TABLE genres ADD CONSTRAINT uq_genres_name UNIQUE (name);
ALTER TABLE countries ADD CONSTRAINT uq_countries_name UNIQUE (name);

-- Один отзыв на фильм от пользователя
ALTER TABLE reviews ADD CONSTRAINT uq_reviews_user_film UNIQUE (user_id, film_id);

-- Уникальность названий критериев
ALTER TABLE criteria ADD CONSTRAINT uq_criteria_name UNIQUE (name);


-- ============================================
-- Ограничения (NOT NULL)
-- ============================================

ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ALTER COLUMN username SET NOT NULL;
ALTER TABLE users ALTER COLUMN password_hash SET NOT NULL;
ALTER TABLE genres ALTER COLUMN name SET NOT NULL;
ALTER TABLE countries ALTER COLUMN name SET NOT NULL;
ALTER TABLE films ALTER COLUMN type SET NOT NULL;
ALTER TABLE films ALTER COLUMN last_sync SET NOT NULL;


-- ============================================
-- Ограничения (CHECK)
-- ============================================

-- Оценка фильма от 1 до 10
ALTER TABLE reviews ADD CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 10);

-- Оценка по критерию от 1 до 10
ALTER TABLE review_criteria_ratings ADD CONSTRAINT chk_rcr_rating CHECK (rating BETWEEN 1 AND 10);


-- ============================================
-- Внешние ключи (FOREIGN KEY)
-- ============================================

-- Фильм ↔ Жанры
ALTER TABLE film_genres ADD CONSTRAINT fk_fg_film FOREIGN KEY (film_id) REFERENCES films(kinopoisk_id) ON DELETE CASCADE;
ALTER TABLE film_genres ADD CONSTRAINT fk_fg_genre FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE;

-- Фильм ↔ Страны
ALTER TABLE film_countries ADD CONSTRAINT fk_fc_film FOREIGN KEY (film_id) REFERENCES films(kinopoisk_id) ON DELETE CASCADE;
ALTER TABLE film_countries ADD CONSTRAINT fk_fc_country FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE;

-- Отзывы ↔ Пользователи, Фильмы
ALTER TABLE reviews ADD CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reviews ADD CONSTRAINT fk_reviews_film FOREIGN KEY (film_id) REFERENCES films(kinopoisk_id) ON DELETE CASCADE;

-- Оценки по критериям ↔ Отзывы, Критерии
ALTER TABLE review_criteria_ratings ADD CONSTRAINT fk_rcr_review FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE;
ALTER TABLE review_criteria_ratings ADD CONSTRAINT fk_rcr_criterion FOREIGN KEY (criterion_id) REFERENCES criteria(id) ON DELETE CASCADE;


-- ============================================
-- Индексы
-- ============================================

-- Поиск фильмов по названию
CREATE INDEX idx_films_name_ru ON films(name_ru);

-- Фильтрация по году
CREATE INDEX idx_films_year ON films(year);

-- Сортировка по рейтингу
CREATE INDEX idx_films_rating ON films(rating_kinopoisk);

-- Фильтрация по типу (FILM, TV_SERIES и т.д.)
CREATE INDEX idx_films_type ON films(type);

-- Быстрый поиск фильмов по жанру
CREATE INDEX idx_film_genres_genre ON film_genres(genre_id);

-- Быстрый поиск фильмов по стране
CREATE INDEX idx_film_countries_country ON film_countries(country_id);

-- Отзывы по конкретному фильму
CREATE INDEX idx_reviews_film ON reviews(film_id);

-- Все отзывы конкретного пользователя
CREATE INDEX idx_reviews_user ON reviews(user_id);
