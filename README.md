# Spring Boot Web Application Template

A comprehensive Spring Boot template project with H2 database, Thymeleaf templates, and a modern responsive web interface.

## Features

- Spring Boot 3.x with Java 17
- H2 in-memory database with JPA/Hibernate
- RESTful API endpoints and error handling
- Thymeleaf templating engine for server-side rendering
- Responsive front-end with modern CSS
- JavaScript for enhanced user experience
- Complete CRUD operations example

## Technology Stack

- **Backend**: Spring Boot, Spring Data JPA, H2 Database
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Build Tool**: Maven

## Project Structure

```
src/main/
├── java/com/example/demo/
│   ├── controller/       # Web and REST controllers
│   ├── entity/           # JPA entity classes
│   ├── repository/       # Spring Data repositories
│   └── DemoApplication.java  # Main application class
├── resources/
│   ├── static/
│   │   ├── css/          # CSS stylesheets
│   │   └── js/           # JavaScript files
│   ├── templates/        # Thymeleaf HTML templates
│   ├── application.properties  # Application configuration
│   └── data.sql          # Initial database data
```

## Getting Started

### Prerequisites

- Java 17 or later
- Maven

### Using This Template

1. Clone or download this repository
2. Customize to your needs:
   - Update package names in Java files
   - Modify entity classes for your domain
   - Adjust database settings in `application.properties`
   - Customize HTML templates and CSS

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

## Available Pages

- **Home Page**: http://localhost:8080/ - Landing page with application overview
- **Items Management**: http://localhost:8080/items - Create, read, update, and delete items
- **H2 Console**: http://localhost:8080/h2-console - Database management interface

## API Endpoints

The application provides a RESTful API:

- GET `/api/items` - Get all items
- GET `/api/items/{id}` - Get item by ID
- POST `/api/items` - Create a new item
- PUT `/api/items/{id}` - Update an existing item
- DELETE `/api/items/{id}` - Delete an item

## H2 Database

Access the H2 database console at: http://localhost:8080/h2-console

Connection details:
- JDBC URL: `jdbc:h2:mem:demodb`
- Username: `sa`
- Password: `password`

## Customization Guide

### Adding New Entities

1. Create a new entity class in the `entity` package
2. Create a repository interface in the `repository` package
3. Create controller classes for UI and API endpoints
4. Create or update Thymeleaf templates

### Changing Database Configuration

Modify `application.properties` to use a different database:

```properties
# MySQL example
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

## Deployment

To build a deployable JAR file:

```bash
mvn clean package
```

The JAR will be created in the `target` directory and can be run with:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Contributing

Contributions are welcome! Feel free to fork this template and submit pull requests.

## License

This template is available under the [MIT License](LICENSE). 