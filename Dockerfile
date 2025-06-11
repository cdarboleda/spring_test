## Con certirtifacado autofirmado
# FROM openjdk:21-jdk-slim

# WORKDIR /app

# # Copia el jar de la aplicación
# COPY build/libs/securityapi-0.0.1-SNAPSHOT.jar backend-app.jar

# # Copia el truststore al contenedor
# COPY src/main/resources/certs/keycloak-truststore.jks /app/certs/keycloak-truststore.jks

# EXPOSE 9090

# # Usamos variables de entorno para el truststore
# ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/app/certs/keycloak-truststore.jks", "-Djavax.net.ssl.trustStorePassword=posgrado123", "-Djavax.net.ssl.trustStoreType=JKS", "-jar", "backend-app.jar"]
###########################

# # Con certificado valido

# FROM openjdk:21-jdk-slim

# WORKDIR /app

# # Copia el jar de la aplicación
# COPY build/libs/securityapi-0.0.1-SNAPSHOT.jar backend-app.jar

# EXPOSE 9090

# # Ejecuta la app sin truststore personalizado
# ENTRYPOINT ["java", "-jar", "backend-app.jar"]



###########################

#themes keycloak

FROM quay.io/keycloak/keycloak:26.2.1

# Copia los themes personalizados al directorio de Keycloak
COPY themes /opt/keycloak/themes
