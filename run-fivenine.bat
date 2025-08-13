@echo off
echo ========================================
echo    FIVENINE SOCIAL MOVIE PLATFORM
echo        Standalone Executable v3.0
echo ========================================
echo.
echo Starting Fivenine server with H2 embedded database...
echo Backend API: http://localhost:8080
echo Frontend UI: http://localhost:8080
echo.
echo Press Ctrl+C to stop the server
echo.

java -Dspring.profiles.active=prod -jar target/fivenine-0.0.1-SNAPSHOT.jar

echo.
echo Server stopped.
pause