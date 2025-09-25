# ===== build: Maven + JDK 17 (sin mvnw) =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copia POM primero para cachear dependencias
COPY pom.xml .
# Baja dependencias para cache, sin tests (acelera builds repetidos)
RUN mvn -B -e -DskipTests dependency:go-offline

# Copia el código
COPY src ./src

# Empaqueta WAR con logs "normales" (usa -X si quieres debug)
RUN mvn -B -e -DskipTests package

# ===== runtime: Tomcat 10 + JDK 17 =====
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat
# Limpia apps por defecto
RUN rm -rf webapps/*

# Variables para tu DbContext (lee con System.getenv)
ENV DB_URL="" DB_USER="" DB_PASSWORD=""

# Copia el WAR compilado como ROOT.war
COPY --from=build /workspace/target/*.war webapps/ROOT.war

EXPOSE 8001
HEALTHCHECK --interval=15s --timeout=5s --retries=10 \
  CMD wget -qO- http://localhost:8001/ || exit 1

CMD ["catalina.sh", "run"]
