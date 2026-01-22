# Medicine Manager

A Spring Boot application for managing medicine inventory with REST API and web UI.

## Tech Stack

- **Backend:** Spring Boot 4.0.1, Java 17
- **Database:** H2 (in-memory)
- **Frontend:** Thymeleaf, Tailwind CSS
- **Build:** Maven

## Quick Start

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/medicines` | Get all medicines |
| GET | `/api/medicines/pages?page=0&size=10` | Paginated list |
| GET | `/api/medicines/{id}` | Get by ID |
| GET | `/api/medicines/stats` | Dashboard statistics |
| GET | `/api/medicines/expired` | Get expired medicines |
| GET | `/api/medicines/low-stock?stockValue=10` | Get low stock |
| GET | `/api/medicines/search?name=x&manufacturer=PFIZER` | Search |
| POST | `/api/medicines` | Create medicine |
| PUT | `/api/medicines/{id}` | Update medicine |
| PATCH | `/api/medicines/{id}/increase` | Increase stock |
| PATCH | `/api/medicines/{id}/decrease` | Decrease stock |
| DELETE | `/api/medicines/{id}` | Delete medicine |

## Web UI

| Page | URL |
|------|-----|
| Dashboard | `/` |
| Medicine List | `/medicines` |
| Expired | `/medicines/expired` |
| Low Stock | `/medicines/low-stock` |

## H2 Console

Access database console at http://localhost:8080/h2-console

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(empty)*

## Project Structure

```
src/main/java/org/example/medicine_api/
├── controller/
│   ├── MedicineController.java      # REST API
│   └── web/                         # Web UI controllers
├── dto/
├── exception/
├── model/
├── repository/
└── service/

src/main/resources/
├── templates/                       # Thymeleaf templates
│   ├── layout/
│   ├── dashboard/
│   └── medicine/
├── application.yaml
├── schema.sql
└── data.sql
```

## Manufacturers

Available values: `PFIZER`, `BAYER`, `NOVARTIS`, `JOHNSON_AND_JOHNSON`, `ROCHE`
