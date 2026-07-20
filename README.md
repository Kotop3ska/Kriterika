# Kriterika (RU)

> Сервис для оценки фильмов по критериям

---

## Идея

Данный проект реализует веб-сервис для оценки фильмов по критериям с интеграцией Kinopoisk API.

Приложение построено по трёхуровневой архитектуре: СУБД PostgreSQL + серверная часть на Java (Spring Boot) + клиентская часть на React (TypeScript).

Функциональность:
- Поиск фильмов с фильтрацией по жанру, стране и году
- Просмотр карточек фильмов с информацией из Kinopoisk API
- Регистрация и авторизация (JWT)
- Создание, редактирование и удаление отзывов
- Оценка фильмов по 10 критериям (сюжет, актёрская игра, визуал и др.)

---

## Запуск приложения

### С помощью Docker (рекомендуется)

Для запуска необходим Docker и Docker Compose.

1. Клонируйте репозиторий:
```bash
git clone https://github.com/Kotop3ska/Kriterika.git
cd Kriterika
```

2. В файле `docker-compose.yml` замените `YOUR_API_KEY_HERE` на ключ Kinopoisk API (см. раздел "Получение API ключа").

3. Запустите приложение:
```bash
docker compose up -d --build
```

4. Откройте в браузере: http://localhost

### Ручной запуск (для разработки)

Для ручного запуска необходимы: Java 21+, Node.js 20+, PostgreSQL 16.

1. Запустите базу данных:
```bash
cd database
docker compose up -d
docker exec -i kriterika_db psql -U admin -d kriterika < create_tables.sql
docker exec -i kriterika_db psql -U admin -d kriterika < seed_data.sql
```

2. В файле `kriterika/src/main/resources/application.properties` замените `YOUR_API_KEY_HERE`.

3. Запустите сервер:
```bash
cd kriterika
./mvnw spring-boot:run
```

4. В другом терминале запустите клиент:
```bash
cd client
npm install
npm run dev
```

5. Откройте в браузере: http://localhost:5173

---

## Получение API ключа

Для работы с данными Kinopoisk необходим API ключ:

1. Перейдите на https://kinopoiskapiunofficial.tech
2. Зарегистрируйтесь
3. В личном кабинете скопируйте API ключ
4. Вставьте его в `docker-compose.yml` (или `application.properties` при ручном запуске)

---

## Стек технологий

**Сервер:**
- Java 21, Spring Boot 4.1
- Spring Data JPA, Hibernate
- PostgreSQL 16
- Spring Security + JWT

**Клиент:**
- React 19, TypeScript
- Material UI
- Vite
- Axios

---

# Kriterika (EN)

> A web service for rating films by multiple criteria

---

## Idea

This project implements a web service for rating films by multiple criteria with Kinopoisk API integration.

The application is built using a three-tier architecture: PostgreSQL DBMS + Java server (Spring Boot) + React client (TypeScript).

Functionality:
- Film search with filtering by genre, country, and year
- Film card viewing with data from Kinopoisk API
- Registration and authorization (JWT)
- Creating, editing, and deleting reviews
- Rating films by 10 criteria (plot, acting, visuals, etc.)

---

## Running the Application

### Using Docker (recommended)

Docker and Docker Compose are required.

1. Clone the repository:
```bash
git clone https://github.com/Kotop3ska/Kriterika.git
cd Kriterika
```

2. In the `docker-compose.yml` file, replace `YOUR_API_KEY_HERE` with a Kinopoisk API key (see "Getting an API Key" section).

3. Start the application:
```bash
docker compose up -d --build
```

4. Open in browser: http://localhost

### Manual setup (for development)

Java 21+, Node.js 20+, PostgreSQL 16 are required.

1. Start the database:
```bash
cd database
docker compose up -d
docker exec -i kriterika_db psql -U admin -d kriterika < create_tables.sql
docker exec -i kriterika_db psql -U admin -d kriterika < seed_data.sql
```

2. In `kriterika/src/main/resources/application.properties`, replace `YOUR_API_KEY_HERE`.

3. Start the server:
```bash
cd kriterika
./mvnw spring-boot:run
```

4. In another terminal, start the client:
```bash
cd client
npm install
npm run dev
```

5. Open in browser: http://localhost:5173

---

## Getting an API Key

A Kinopoisk API key is required for the application to work:

1. Go to https://kinopoiskapiunofficial.tech
2. Register
3. Copy the API key from your dashboard
4. Paste it into `docker-compose.yml` (or `application.properties` for manual setup)

---

## Tech Stack

**Server:**
- Java 21, Spring Boot 4.1
- Spring Data JPA, Hibernate
- PostgreSQL 16
- Spring Security + JWT

**Client:**
- React 19, TypeScript
- Material UI
- Vite
- Axios
