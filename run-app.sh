#!/bin/bash

# Simple script to run the Spring Boot application with different database options

# Function to check if MySQL is installed
check_mysql() {
  if ! command -v mysql &> /dev/null; then
    echo "MySQL is not installed. Installing MySQL..."
    
    # Check OS type and install MySQL accordingly
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
      # For Ubuntu/Debian
      if command -v apt-get &> /dev/null; then
        sudo apt-get update
        sudo apt-get install -y mysql-server
      # For CentOS/RHEL
      elif command -v yum &> /dev/null; then
        sudo yum install -y mysql-server
        sudo systemctl start mysqld
        sudo systemctl enable mysqld
      fi
    elif [[ "$OSTYPE" == "darwin"* ]]; then
      # For macOS
      if command -v brew &> /dev/null; then
        brew install mysql
        brew services start mysql
      else
        echo "Please install Homebrew first: https://brew.sh/"
        exit 1
      fi
    elif [[ "$OSTYPE" == "msys"* ]]; then
      # For Windows (Git Bash)
      echo "Please install MySQL manually on Windows: https://dev.mysql.com/downloads/installer/"
      exit 1
    else
      echo "Unsupported operating system. Please install MySQL manually."
      exit 1
    fi
  fi

  # Check if MySQL service is running
  if ! mysqladmin ping -h localhost -u root --silent; then
    echo "Starting MySQL service..."
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
      sudo systemctl start mysql
    elif [[ "$OSTYPE" == "darwin"* ]]; then
      brew services start mysql
    fi
  fi
}

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
    check_mysql
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