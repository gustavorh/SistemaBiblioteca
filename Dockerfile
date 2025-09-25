# ===== build: usa el wrapper y JDK 17 =====
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace
# Copiamos todo porque mvnw y .mvn están en el repo
COPY . .
# Permisos por si el wrapper no es ejecutable
RUN chmod +x mvnw

# Construye con log VERBOSO y sin tests
# (sin -q, con -X para stack trace si falla; puedes quitar -X tras arreglar)
RUN ./mvnw -V --no-transfer-progress -DskipTests -X package

# ===== runtime: Tomcat 10 con JDK 17 =====
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat
# Limpia apps por defecto
RUN rm -rf webapps/*
# Variables para DB (leídas por tu DbContext vía System.getenv)
ENV DB_URL="" DB_USER="" DB_PASSWORD=""
# Copia el WAR como ROOT.war
COPY --from=build /workspace/target/*.war webapps/ROOT.war

EXPOSE 8001
HEALTHCHECK --interval=15s --timeout=5s --retries=10 CMD wget -qO- http://localhost:8001/ || exit 1
CMD ["catalina.sh", "run"]
