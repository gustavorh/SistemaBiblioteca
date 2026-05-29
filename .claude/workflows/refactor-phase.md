# Workflow: refactor-phase

Ejecuta una fase completa del roadmap de refactor de Librio de forma estructurada y segura.

## Cuándo usar

- Al iniciar una nueva fase del roadmap (`docs/REFACTORING_ROADMAP.md`).
- Para fan-out de tareas dentro de una fase (ej. migrar los 12 repos en Fase 1).

## Fases disponibles

Pasar el número de fase como argumento: `refactor-phase 1`

## Proceso general

### Pre-flight (toda fase)

```bash
# 1. Verificar que el build está limpio
./mvnw -DskipTests clean package

# 2. Verificar tests en verde (si ya hay tests)
./mvnw test

# 3. Crear branch de trabajo
git checkout -b refactor/fase-N
```

### Fase 0 — Red de seguridad

**Agentes:**
- Crear estructura `src/test/` con paquete espejo
- Agregar H2 a `pom.xml` (scope test)
- Escribir test de smoke por repositorio (fan-out: 1 agente por repo)
- Crear `.github/workflows/ci.yml`

**Verificación:**
```bash
./mvnw test  # debe ser verde
./mvnw -DskipTests clean package  # debe generar LibraryMS.war
```

### Fase 1 — Pool de conexiones + AbstractJdbcRepository

**Agentes (fan-out):**
- Agente principal: refactorizar `DbContext` + crear `AbstractJdbcRepository`
- 12 agentes en paralelo: uno por cada `Jdbc*Repository` para migrar
- Agente de verificación: correr build + tests, revisar que no hay `DriverManager` en servlets

**Verificación:**
```bash
# No debe haber DriverManager fuera de DbContext
grep -rn "DriverManager.getConnection" src/main/java/ | grep -v "DbContext"

./mvnw test
```

### Fase 2 — De-duplicar capa web

**Agentes (fan-out):**
- Agente principal: crear `CrudServlet<T>` base + `AppContextListener`
- 11 agentes en paralelo: uno por cada `*Servlet` para migrar
- Agente de verificación: no debe haber `new JdbcRepositoryFactory` en servlets

**Verificación:**
```bash
grep -rn "new JdbcRepositoryFactory\|new DefaultServiceFactory" src/main/java/dev/gustavorh/lms_dev_10/presentation/

./mvnw test
```

### Fase 3 — CDI

**Agentes:**
- Actualizar `pom.xml` (Weld Servlet)
- Crear `beans.xml`
- Anotar beans + migrar inyecciones

**Verificación:**
```bash
./mvnw -DskipTests clean package
# Levantar app y verificar que CDI inicializa sin errores en los logs de Tomcat
```

### Fase 4 — Dominio

**Agentes:**
- Agente de dominio: reglas en `Member`, `Inventory`, `Loan`
- Agente de tests: tests unitarios de reglas de dominio

**Verificación:**
```bash
./mvnw test
# Casos: préstamo con miembro inactivo → rechazado; stock no negativo; devolución incrementa stock
```

### Fase 5 — Cross-cutting

**Agentes:**
- Agregar SLF4J/Logback + reemplazar `printStackTrace`
- Crear `SecurityHeadersFilter`
- Configurar `<error-page>` en `web.xml`

**Verificación:**
```bash
grep -rn "printStackTrace" src/main/java/  # debe dar 0 resultados
./mvnw test
# Verificar headers: curl -I http://localhost:8080/LibraryMS/
```

## Post-fase

1. Actualizar checkboxes en `docs/REFACTORING_ROADMAP.md`.
2. Actualizar hallazgos resueltos en `docs/SECURITY_AUDIT.md` (si aplica).
3. Commit con mensaje: `refactor: fase N — <descripción>`.
4. Crear PR hacia `master`.
