# CarbonEdge API

Spring Boot backend scaffold for the ecommerce platform, initialized from the provided `openapi.yml` and limited to the Product module.

## Included

- Java 21 + Maven Spring Boot project
- Product module: controller, service, repository, JPA entity
- DTO model generation from `openapi.yml` via `openapi-generator-maven-plugin`
- MySQL configuration by default, with optional local H2 profile
- Swagger UI via Springdoc
- Public product APIs for guest users, with protected write APIs

## Project Structure

```text
src/main/java/th/co/carbonedge/api
├── CarbonedgeApiApplication.java
├── common
├── product
│   ├── controller
│   ├── domain
│   ├── exception
│   ├── repository
│   └── service
└── dto (generated during Maven build)
```

## Prerequisites

- Java 21
- Maven 3.8+
- MySQL 8+ if you want to use the `mysql` profile

## Local Database

You can still start the application with an in-memory H2 database using the `local` profile:

- H2 console: `http://localhost:8080/api/h2-console`
- JDBC URL: `jdbc:h2:mem:carbonedge`
- username: `sa`
- password: empty

To run against MySQL instead:

```bash
export SPRING_PROFILES_ACTIVE=mysql
export DB_URL=jdbc:mysql://203.150.106.14:3306/carbon_edge?useSSL=false\&allowPublicKeyRetrieval=true\&serverTimezone=Asia/Bangkok
export DB_USERNAME=carbon_edge_user
export DB_PASSWORD='0owa?@K079Bc'
```

## Run Locally

```bash
mvn clean spring-boot:run
```

The app will start on `http://localhost:8080`.

## API Access

- Guest users can call `GET /api/v1/products` and `GET /api/v1/products/{productId}` without a token.
- Protected endpoints such as `POST`, `PUT`, and `DELETE` on `/api/v1/products` require authentication and will return `401 Unauthorized` until an auth mechanism is wired in.
- React web apps can call this API cross-origin by setting `APP_CORS_ALLOWED_ORIGINS`, for example `http://localhost:3000,http://localhost:5173`.

## IntelliJ

Open the folder as a Maven project, then reload Maven once so OpenAPI generation stays available from the IDE.

Run the app directly from `CarbonedgeApiApplication`.

If IntelliJ asks for the main class, use:

```text
th.co.carbonedge.api.CarbonedgeApiApplication
```

If you want to run with MySQL from IntelliJ, set:

```text
SPRING_PROFILES_ACTIVE=mysql
```

## OpenAPI and Swagger

- Contract file: `openapi.yml`
- Generated DTOs: `src/main/java/th/co/carbonedge/api/dto`
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`

## Build

```bash
mvn clean verify
```
