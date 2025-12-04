@echo off
REM Script de compilation pour CampusShare (Windows)
REM Usage: compile.bat

echo ========================================
echo     Compilation de CampusShare
echo ========================================
echo.

REM Verifier Java
where javac >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo [ERREUR] javac non trouve. Installez Java JDK.
    exit /b 1
)

echo [OK] Java trouve
echo.

REM Creer le dossier de sortie
if not exist "target\classes" mkdir target\classes

REM Compiler
echo Compilation des fichiers Java...
dir /s /B src\main\java\*.java > sources.txt
javac -d target\classes -sourcepath src\main\java @sources.txt
del sources.txt

if %ERRORLEVEL% equ 0 (
    echo.
    echo [SUCCES] Compilation reussie!
    echo    Classes generees dans: target\classes\
    echo.
    echo    Pour executer: run.bat
) else (
    echo.
    echo [ERREUR] Erreur de compilation
    exit /b 1
)
