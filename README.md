# Kriterika

Сервис для оценки фильмов по критериям с интеграцией Kinopoisk API.

## Быстрый запуск (Docker)

```bash
docker compose up -d --build
```

- Приложение: http://localhost
- API: http://localhost/api/...

## Ручной запуск (для разработки)

### 1. БД
```bash
cd database
docker compose up -d
docker exec -i kriterika_db psql -U admin -d kriterika < create_tables.sql
docker exec -i kriterika_db psql -U admin -d kriterika < seed_data.sql
```

### 2. Сервер (требуется Java 21+)
```bash
cd kriterika
./mvnw spring-boot:run
```

### 3. Клиент (требуется Node.js 20+)
```bash
cd client
npm install
npm run dev
```

## API Key

Нужен ключ Kinopoisk Unofficial API: https://kinopoiskapiunofficial.tech

Для Docker — прописан в `docker-compose.yml`.

Для ручного запуска — в `kriterika/src/main/resources/application.properties`:
```
kinopoisk.api.key=ТВОЙ_КЛЮЧ
```

## Стек

- **Бэкенд:** Spring Boot 4.1, Java 21, PostgreSQL 16, Hibernate
- **Фронтенд:** React 19, TypeScript, Material UI, Vite
- **Авторизация:** JWT (bcrypt + JJWT)
- **API:** Kinopoisk Unofficial API v2.2
