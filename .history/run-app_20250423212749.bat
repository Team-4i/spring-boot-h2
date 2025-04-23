@echo off
REM Simple script to run the Spring Boot application with different database options

REM Function to display help message
:show_help
echo Usage: run-app.bat [option]
echo Options:
echo   h2      Run with H2 in-memory database (default)
echo   mysql   Run with MySQL database
echo   help    Show this help message
echo.
echo Examples:
echo   run-app.bat          # Runs with H2 database
echo   run-app.bat h2       # Runs with H2 database
echo   run-app.bat mysql    # Runs with MySQL database
exit /b

REM Process command line arguments
if "%1"=="" goto h2
if "%1"=="h2" goto h2
if "%1"=="mysql" goto mysql
if "%1"=="help" goto :show_help

echo Unknown option: %1
call :show_help
exit /b 1

:h2
echo Starting application with H2 database...
mvn spring-boot:run -Dspring-boot.run.profiles=dev
exit /b

:mysql
echo Starting application with MySQL database...
mvn spring-boot:run -Dspring-boot.run.profiles=prod
exit /b 