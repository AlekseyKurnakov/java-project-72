# Анализатор страниц (Java)

[![hexlet-check](https://github.com/AlekseyKurnakov/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/AlekseyKurnakov/java-project-72/actions)
[![Test Coverage](https://raw.githubusercontent.com/AlekseyKurnakov/java-project-72/main/.github/badges/jacoco.svg)](https://github.com/AlekseyKurnakov/java-project-72/actions)

Page Analyzer — сайт, который проверяет указанные страницы на SEO-пригодность: анализирует доступность страницы, код ответа сервера, а также содержимое тегов `title`, `h1` и мета-тега `description`.

## Демо

[Page Analyzer на Railway](https://java-project-72-production-22a9.up.railway.app/)

## Стек

- Java 21, Gradle
- [Javalin](https://javalin.io/) — веб-фреймворк
- [Jte](https://jte.gg/) — шаблонизатор
- [Tailwind CSS](https://tailwindcss.com/) — стили
- [HikariCP](https://github.com/brettwooldridge/HikariCP) — пул соединений с БД
- H2 (для разработки и тестов) / PostgreSQL (продакшен)
- [Unirest](https://kong.github.io/unirest-java/) — HTTP-клиент для проверки сайтов
- [Jsoup](https://jsoup.org/) — парсинг HTML
- JUnit 5, AssertJ, MockWebServer — тестирование

## Установка

Для запуска потребуются установленные JDK 21, Gradle не ниже 8.7 и Node.js (для сборки стилей).

```bash
git clone https://github.com/AlekseyKurnakov/java-project-72.git
cd java-project-72
make setup
```

## Запуск

```bash
make start
```

Команда установит npm-зависимости, соберёт и будет отслеживать изменения в стилях, и запустит приложение в режиме разработки. По умолчанию оно доступно на `http://localhost:8080`.

Локально приложение использует базу данных H2 в памяти. Для подключения к внешней базе данных (например, PostgreSQL в продакшене) укажите переменную окружения `JDBC_DATABASE_URL`:

```bash
export JDBC_DATABASE_URL=jdbc:postgresql://host:5432/dbname?user=user&password=password
```

## Тесты

```bash
./gradlew test
```

---

<details>
<summary>Автоматические тесты Хекслета</summary>

Тесты запускаются на каждый коммит. За запуск отвечает файл `.github/workflows/hexlet-check.yml` — не удаляйте и не переименовывайте ни его, ни репозиторий.

</details>