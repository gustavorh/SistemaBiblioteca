# ===== STAGE: build =====
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# Copiamos TODO para usar mvnw y .mvn tal cual el repo
COPY . .
RUN chmod +x mvnw || true

# Info de entorno para trazar
RUN java -version && ./mvnw -v

# Build con logs detallados; si falla, mostramos reportes y salimos con 1
# -X = debug; --no-transfer-progress = logs más limpios
RUN ./mvnw -DskipTests -X --no-transfer-progress package \
  || (echo '--- SUREFIRE / FAILSAFE REPORTS ---' && \
      (ls -lah target/surefire-reports || true) && \
      (cat target/surefire-reports/*.txt 2>/dev/null || true) && \
      (ls -lah target/failsafe-reports || true) && \
      (cat target/failsafe-reports/*.txt 2>/dev/null || true) && \
      echo '--- EFFECTIVE POM (recorte) ---' && \
      ./mvnw -q help:effective-pom -DskipTests | sed -n '1,200p' && \
      exit 1)

# ===== STAGE: runtime =====
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*
ENV DB_URL="" DB_USER="" DB_PASSWORD=""
COPY --from=build /workspace/target/*.war webapps/ROOT.war
EXPOSE 8001
HEALTHCHECK --interval=15s --timeout=5s --retries=10 CMD wget -qO- http://localhost:8001/ || exit 1
CMD ["catalina.sh", "run"]
