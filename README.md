# Medicine Inventory API

A REST API for managing pharmacy medicine inventory, built with Spring Boot 4.0.1.

## Features

- CRUD operations for medicines
- Pagination support
- Expired and low-stock medicine tracking
- Dashboard statistics
- Search by name and manufacturer
- Swagger/OpenAPI documentation
- Input validation and error handling

## Tech Stack

- Java 17, Spring Boot 4.0.1
- Spring Data JPA, H2 in-memory database
- Lombok, Springdoc OpenAPI 3.0.1
- JUnit 5, Mockito

## Getting Started

```bash
./mvnw spring-boot:run
```

The server starts on **http://localhost:8081**.

- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, no password)

## API Endpoints

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

## Manufacturers

`PFIZER`, `BAYER`, `NOVARTIS`, `JOHNSON_AND_JOHNSON`, `ROCHE`

## Running Tests

```bash
./mvnw test
```
