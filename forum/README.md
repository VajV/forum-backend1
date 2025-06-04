# Forum API

Backend для форума по Mobile Legends: Bang Bang — REST API на Spring Boot для публикации гайдов, постов и комментариев.

## 🚀 Технологии

- Java 17
- Spring Boot 3.4.x
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok
- Swagger/OpenAPI (springdoc-openapi)
- Gradle

## 📦 Быстрый старт

1. **Клонируй репозиторий**  
   (или распакуй архив):
   ```bash
   git clone https://github.com/your-username/forum-api.git
   cd forum-api

2. **Настрой БД**

По умолчанию используется PostgreSQL, настройки в src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/forumdb
spring.datasource.username=postgres
spring.datasource.password=rootroot

Создай БД вручную, если нужно:

CREATE DATABASE forumdb;

3. **Собери и запусти проект**

./gradlew bootRun
Или через IDE (IntelliJ IDEA: кнопка Run на классе ForumApplication)

4. **Открой Swagger UI**
http://localhost:8080/swagger-ui.html

API (Swagger)
Полное описание ручек смотри в Swagger UI:
http://localhost:8080/swagger-ui.html

**Структура проекта**
src/main/java/com/example/forum/
  controller/    # REST-контроллеры (API)
  service/       # Бизнес-логика
  repository/    # Работа с БД
  model/         # JPA-сущности
  dto/           # Data Transfer Object (DTO)
  exception/     # Кастомные исключения
  exceptionHandler/ # Глобальный обработчик ошибок
src/main/resources/
  application.properties # Настройки подключения
build.gradle      # зависимости и плагины

**Что реализовано**
Категории, посты и комментарии

Создание, просмотр, валидация (Bean Validation)

DTO для входящих/исходящих данных

Логирование действий (SLF4J)

Swagger UI

Глобальная обработка ошибок (RestControllerAdvice)

**Roadmap (что можно улучшить)**
Добавить тесты (unit/integration)

Пагинация списков (для категорий, постов, комментариев)

Поддержка CORS для фронта

Авторизация (JWT/OAuth)

Dockerfile/Compose для лёгкого запуска

**Автор**
KukyoUmi
GitHub - https://github.com/VajV

