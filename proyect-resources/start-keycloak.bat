@echo off
echo Configurando Keycloak...

REM Database
set KC_DB=postgres
set KC_DB_URL=jdbc:postgresql://localhost:5432/proyecto_db
set KC_DB_SCHEMA=keycloak_schema
set KC_DB_USERNAME=postgres
set KC_DB_PASSWORD=postgres

REM Server
set KC_HTTP_PORT=8080
set KC_HOSTNAME=localhost

REM Admin
set KC_BOOTSTRAP_ADMIN_USERNAME=admin
set KC_BOOTSTRAP_ADMIN_PASSWORD=admin

echo Iniciando Keycloak...
bin\kc.bat start-dev
pause