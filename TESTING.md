# Тестирование API без GUI

## Вариант 1: Postman / Insomnia
Графический клиент для HTTP-запросов. Настраиваешь endpoints, отправляешь запросы, смотришь ответы.
Скачай Postman: https://www.postman.com/downloads/

## Вариант 2: cURL (в терминале)
```bash
# Получить список фильмов
curl http://localhost:8080/api/films?keyword=матрица

# Создать отзыв
curl -X POST http://localhost:8080/api/reviews \
  -H "Content-Type: application/json" \
  -d '{"filmId": 301, "rating": 9, "title": "Шедевр"}'
```

## Вариант 3: Swagger UI
Подключаешь Springdoc OpenAPI — получаешь веб-интерфейс с описанием всех эндпоинтов и кнопкой "Try it out".

## Вариант 4: JUnit + MockMvc (автотесты)
Пишешь тесты прямо в Java — автоматизированно, без ручных запросов.

---

## Рекомендация
Начни с Postman/cURL для ручного тестирования, параллельно пиши JUnit-тесты для автоматизации.
