# Usa una imagen de Maven para construir el JAR
FROM maven:3.9.1-openjdk-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y el código fuente
COPY pom.xml .
COPY src /app/src

# Construye el JAR
RUN mvn clean package -DskipTests

# Usa una imagen de JDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR del contenedor de construcción
COPY --from=build /app/target/apiDux-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que tu aplicación escuchará
EXPOSE 8080

# Define el comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]


#docker build -t api-dux .
#docker run -p 8080:8080 api-dux