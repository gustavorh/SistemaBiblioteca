# ===== build ⛏️: empaqueta WAR con Maven =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
# Pre-descarga dependencias para cachear
RUN mvn -B -e -q -DskipTests dependency:go-offline
COPY src ./src
# Empaqueta WAR (ajusta si tu packaging es distinto)
RUN mvn -B -e -DskipTests package

# ===== runtime 🚀: Tomcat 10 (Jakarta) =====
FROM tomcat:10.1-jdk21-temurin AS runtime
# Opcional: endurecer Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Variables de entorno para tu DbContext (léelas con System.getenv)
ENV DB_URL = System.getenv("DB_URL");
ENV DB_USER = System.getenv("DB_USER");
ENV DB_PASSWORD = System.getenv("DB_PASSWORD");
# SQL Server driver (ya lo tendrá tu app si lo declaraste en pom.xml)

# Copiamos WAR como ROOT.war (sirve en '/')
COPY --from=build /workspace/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8001
HEALTHCHECK --interval=15s --timeout=5s --retries=10 \
  CMD wget -qO- http://localhost:8001/ || exit 1

CMD ["catalina.sh", "run"]
