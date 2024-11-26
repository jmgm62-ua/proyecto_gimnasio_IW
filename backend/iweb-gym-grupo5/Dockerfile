FROM eclipse-temurin:17-jdk-alpine

# Copiar el jar generado
COPY target/*.jar app.jar

# Configurar el generador de números aleatorios para mejor rendimiento
# y evitar bloqueos en entornos cloud
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/app.jar"]
