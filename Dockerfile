# ===== build: Maven + JDK 17 =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Cache de dependencias
COPY pom.xml .
RUN mvn -B -e -DskipTests dependency:go-offline

# Código fuente
COPY src ./src

# Empaquetar WAR
RUN mvn -B -e -DskipTests package

# ===== runtime: Tomcat 10.1 + JDK 17 =====
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

# Limpia apps por defecto
RUN rm -rf webapps/*

# Vars de entorno para tu DbContext
ENV DB_URL="" DB_USER="" DB_PASSWORD=""

# Copia WAR como ROOT
COPY --from=build /workspace/target/*.war webapps/ROOT.war

EXPOSE 8001
HEALTHCHECK --interval=15s --timeout=5s --retries=10 \
  CMD wget -qO- http://localhost:8001/ || exit 1

CMD ["catalina.sh", "run"]
