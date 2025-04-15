#!/bin/bash

# Simple script to run the Spring Boot application with different database options

# Function to display help message
show_help() {
  echo "Usage: ./run-app.sh [option]"
  echo "Options:"
  echo "  h2      Run with H2 in-memory database (default)"
  echo "  mysql   Run with MySQL database"
  echo "  help    Show this help message"
  echo ""
  echo "Examples:"
  echo "  ./run-app.sh          # Runs with H2 database"
  echo "  ./run-app.sh h2       # Runs with H2 database"
  echo "  ./run-app.sh mysql    # Runs with MySQL database"
}

# Process command line arguments
case "$1" in
  h2|"")
    echo "Starting application with H2 database..."
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ;;
  mysql)
    echo "Starting application with MySQL database..."
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
    ;;
  help)
    show_help
    ;;
  *)
    echo "Unknown option: $1"
    show_help
    exit 1
    ;;
esac 