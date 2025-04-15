# Spring Boot Demo with H2 Database

A simple Spring Boot application with H2 in-memory database.

## Features

- RESTful API for managing items
- H2 in-memory database
- Spring Data JPA integration
- H2 Console for database exploration

## Running the Application

### Prerequisites

- Java 17 or later
- Maven

### Build and Run

1. Clone or download the project
2. Navigate to the project directory
3. Run the following command:

```bash
mvn spring-boot:run
```

The application will start on port 8080.

## API Endpoints

- GET `/api/items` - Get all items
- GET `/api/items/{id}` - Get item by ID
- POST `/api/items` - Create a new item
- PUT `/api/items/{id}` - Update an existing item
- DELETE `/api/items/{id}` - Delete an item

## H2 Console

Access the H2 database console at: http://localhost:8080/h2-console

Connection details:
- JDBC URL: `jdbc:h2:mem:demodb`
- Username: `sa`
- Password: `password`

## Example API Usage

### Create a new item

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"New Item","description":"Description of new item"}' http://localhost:8080/api/items
```

### Get all items

```bash
curl http://localhost:8080/api/items
``` 