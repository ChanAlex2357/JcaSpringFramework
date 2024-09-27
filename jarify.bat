@echo off
REM Nom du fichier JAR
set JAR_NAME=jca-spring-framework.jar
REM Chemin vers le dossier source
set SOURCE_DIR=.\src
REM Chemin vers le dossier de destination du JAR
set OUTPUT_DIR=.\dist
REM Le dossier des fichiers compilés
set BIN_DIR=.\bin

set README=README.md

REM Supprimer le dossier de destination s'il existe déjà
if exist "%OUTPUT_DIR%" (
  rmdir /s /q "%OUTPUT_DIR%"
)

REM Copier les fichiers compilés
xcopy /E /I /Q "%BIN_DIR%" "%OUTPUT_DIR%"
  
REM Créer le dossier lib s'il n'existe pas déjà
if not exist "%OUTPUT_DIR%\lib" (
  mkdir "%OUTPUT_DIR%\lib"
)

@REM REM Copier les dépendances de la bibliothèque dans le dossier lib
@REM xcopy /Y "lib\*.jar" "%OUTPUT_DIR%\lib"


REM Copier le readme dans la distribution
xcopy /Y "%README%" "%OUTPUT_DIR%"

REM Créer le fichier JAR
jar cf "%JAR_NAME%" -C "%OUTPUT_DIR%" .

echo Le framework a ete deploye avec succes sous forme de fichier JAR.
