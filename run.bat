@echo off
REM Script d'execution pour CampusShare (Windows)
REM Usage: run.bat

if not exist "target\classes" (
    echo [ERREUR] Projet non compile. Executez d'abord: compile.bat
    exit /b 1
)

java -cp target\classes com.campusshare.CampusShareApp
