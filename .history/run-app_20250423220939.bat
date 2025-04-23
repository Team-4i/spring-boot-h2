@echo off
setlocal enabledelayedexpansion

REM Get the first argument
set "arg=%~1"

REM If no argument or argument is h2, run with H2
if "%arg%"=="" goto run_h2
if /i "%arg%"=="h2" goto run_h2
if /i "%arg%"=="mysql" goto run_mysql
if /i "%arg%"=="help" goto show_help

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

:run_h2
echo Starting application with H2 database...
call mvn spring-boot:run -Dspring-boot.run.profiles=dev
exit /b

:run_mysql
echo Checking MySQL connection...

REM Extract current MySQL password from properties file
for /f "tokens=2 delims==" %%a in ('findstr "spring.datasource.password" src\main\resources\application-prod.properties') do set "current_password=%%a"

:check_connection
echo Attempting to connect to MySQL...
mysql -u root -p%current_password% -e "SELECT 1;" > nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo MySQL connection successful!
    goto start_mysql
) else (
    echo MySQL connection failed!
    set /p new_password="Enter correct MySQL root password: "
    if "%new_password%"=="" (
        echo Password cannot be empty. Please try again.
        goto check_connection
    )
    
    REM Update password in properties file
    powershell -Command "(Get-Content 'src\main\resources\application-prod.properties') -replace 'spring.datasource.password=.*', 'spring.datasource.password=%new_password%' | Set-Content 'src\main\resources\application-prod.properties'"
    set "current_password=%new_password%"
    goto check_connection
)

:start_mysql
echo Starting application with MySQL database...
call mvn spring-boot:run -Dspring-boot.run.profiles=prod
exit /b 