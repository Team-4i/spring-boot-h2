# Spring Boot Web Application Template

A comprehensive Spring Boot template project with multiple database options (H2 in-memory or MySQL), Thymeleaf templates, and a modern responsive web interface.

## Features

- Spring Boot 3.x with Java 17
- Dual database support:
  - H2 in-memory database for development
  - MySQL for production
- RESTful API endpoints and error handling
- Thymeleaf templating engine for server-side rendering
- Responsive front-end with modern CSS
- JavaScript for enhanced user experience
- Complete CRUD operations example

## Technology Stack

- **Backend**: Spring Boot, Spring Data JPA, H2/MySQL Database
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Build Tool**: Maven

## Project Structure

```
src/main/
├── java/com/example/demo/
│   ├── config/         # Configuration classes
│   ├── controller/     # Web and REST controllers
│   ├── entity/         # JPA entity classes
│   ├── repository/     # Spring Data repositories
│   └── DemoApplication.java  # Main application class
├── resources/
│   ├── static/
│   │   ├── css/        # CSS stylesheets
│   │   └── js/         # JavaScript files
│   ├── templates/      # Thymeleaf HTML templates
│   ├── application.properties     # Common configuration
│   ├── application-dev.properties # H2 database configuration
│   ├── application-prod.properties # MySQL configuration
│   ├── data.sql        # H2 sample data
│   ├── data-mysql.sql  # MySQL sample data
│   └── schema-mysql.sql # MySQL schema
```

## Getting Started

### Prerequisites

- Java 17 or later
- Maven
- MySQL (if using production profile)

### Using This Template

1. Clone or download this repository
2. Customize to your needs:
   - Update package names in Java files
   - Modify entity classes for your domain
   - Adjust database settings in properties files
   - Customize HTML templates and CSS

### Database Configuration

The application supports two database modes:
1. **H2 In-Memory Database** - For development (default)
2. **MySQL Database** - For production

#### MySQL Configuration

Before using MySQL, follow these steps:

1. Make sure MySQL is installed and running
2. Create a database: `CREATE DATABASE proddb;` (the app can now create this automatically)
3. **Update MySQL credentials** in `application-prod.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_actual_password
   ```
   (Replace `your_actual_password` with your actual MySQL password)
4. Set `app.init-db=true` in `application-prod.properties` for first-time initialization

### Running the Application

#### Using Convenience Scripts (Recommended)

**For macOS/Linux:**
```bash
# Make the script executable (first time only)
chmod +x run-app.sh

# Run with H2 database (default)
./run-app.sh
# or
./run-app.sh h2

# Run with MySQL database
./run-app.sh mysql

# Show help
./run-app.sh help
```

**For Windows:**
```cmd
# Run with H2 database (default)
run-app.bat
# or
run-app.bat h2

# Run with MySQL database
run-app.bat mysql

# Show help
run-app.bat help
```

#### Using Maven Directly

These commands work on all platforms:

```bash
# Run with H2 database
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run with MySQL database
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

The application will start on http://localhost:8080

## Available Pages

- **Home Page**: http://localhost:8080/ - Landing page with application overview
- **Items Management**: http://localhost:8080/items - Create, read, update, and delete items
- **Database Info**: http://localhost:8080/db-info - View current database configuration
- **H2 Console** (dev profile only): http://localhost:8080/h2-console - Database management interface

## API Endpoints

The application provides a RESTful API:

- GET `/api/items` - Get all items
- GET `/api/items/{id}` - Get item by ID
- POST `/api/items` - Create a new item
- PUT `/api/items/{id}` - Update an existing item
- DELETE `/api/items/{id}` - Delete an item

## Troubleshooting

### Common Issues

1. **No items showing in the UI**: 
   - Make sure the database is properly initialized 
   - Check if `data.sql` is being executed properly
   - Database case sensitivity might cause issues - try using lowercase table names

2. **MySQL Connection Issues**:
   - Verify MySQL is running
   - Check your username and password in `application-prod.properties`
   - Make sure the MySQL port is correct (default: 3306)

3. **H2 Console Not Working**:
   - Make sure you're running in dev profile
   - Verify the console is enabled in properties
   - Access at http://localhost:8080/h2-console

## Deployment

To build a deployable JAR file:

```bash
mvn clean package
```

The JAR will be created in the `target` directory and can be run with:

```bash
# Run with H2 database
java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# Run with MySQL database
java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## License

This template is available under the [MIT License](LICENSE). 