# medicine-api

A small Spring Boot project I built **for learning**. Three things in one place:

1. **REST CRUD** — `@RestController` endpoints for medicines with DTOs, a mapper, and Bean Validation (`@Valid`, `@NotNull`, `@Positive`).
2. **Pagination & queries** — Spring Data JPA `Pageable` results plus derived queries for expired stock, low stock, and name/manufacturer search.
3. **Error handling** — `@RestControllerAdvice` with `GlobalExceptionHandler` turns `NotFoundException` / `ValidationException` and validation errors into clean JSON responses.

Bonus: dashboard stats endpoint (`/api/medicines/stats`) and stock increase/decrease PATCH endpoints with a "stock must be 0 before delete" rule.

Not for production — uses an in-memory H2 database, no authentication, and no persistence between restarts.

## How to run

Requires Java 17.

```bash
git clone git@github.com:rimapaulione/Medicine-api.git
cd Medicine-api
./mvnw spring-boot:run
```

The server starts on **http://localhost:8081**.

Then open:

- Swagger UI — http://localhost:8081/swagger-ui.html
- H2 console — http://localhost:8081/h2-console (JDBC URL `jdbc:h2:mem:testdb`, user `sa`, no password)

## API endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/medicines` | Get all medicines |
| GET | `/api/medicines/pages?page=0&size=10` | Get paginated medicines |
| GET | `/api/medicines/{id}` | Get medicine by ID |
| POST | `/api/medicines` | Create a medicine |
| PUT | `/api/medicines/{id}` | Update a medicine |
| PATCH | `/api/medicines/{id}/increase` | Increase stock |
| PATCH | `/api/medicines/{id}/decrease` | Decrease stock |
| DELETE | `/api/medicines/{id}` | Delete medicine (stock must be 0) |
| GET | `/api/medicines/expired` | Get expired medicines |
| GET | `/api/medicines/low-stock?stockValue=10` | Get low-stock medicines |
| GET | `/api/medicines/stats` | Dashboard statistics |
| GET | `/api/medicines/search?name=x&manufacturer=PFIZER` | Search medicines |

Manufacturers: `PFIZER`, `BAYER`, `NOVARTIS`, `JOHNSON_AND_JOHNSON`, `ROCHE`.

## Running tests

```bash
./mvnw test
```

## Stack

Java 17 · Spring Boot 4.0.1 · Spring Web · Spring Data JPA · H2 · Lombok · springdoc-openapi · JUnit 5 · Mockito
