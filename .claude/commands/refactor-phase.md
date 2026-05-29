# /refactor-phase — Ejecutar una fase del roadmap de refactor

Ejecuta una de las fases del roadmap definido en `docs/REFACTORING_ROADMAP.md`.

## Uso

```
/refactor-phase <número>
```

Ejemplo: `/refactor-phase 1` ejecuta la Fase 1 (pool de conexiones + AbstractJdbcRepository).

## Prerequisitos

- **Fase 0 debe completarse primero** antes de cualquier otra fase.
- Verificar que el build está limpio antes de empezar: `./mvnw -DskipTests clean package`.
- Verificar que los tests de la fase anterior siguen verdes: `./mvnw test`.

## Proceso por fase

### Fase 0 — Red de seguridad
1. Crear estructura `src/test/`
2. Agregar H2 al `pom.xml` (scope test)
3. Escribir tests básicos de repositorios
4. Crear `.github/workflows/ci.yml`
5. Verificar build limpio

### Fase 1 — Pool de conexiones
1. Agregar HikariCP al `pom.xml`
2. Refactorizar `DbContext` para exponer `DataSource`
3. Crear `AbstractJdbcRepository<T, ID>`
4. Migrar los 12 `Jdbc*Repository`
5. Eliminar conexión en `init()` de servlets
6. Correr todos los tests

### Fase 2 — De-duplicar capa web
1. Crear `CrudServlet<T>` base
2. Migrar servlets concretos
3. Crear `AppContextListener`
4. Crear DTOs de entrada
5. Extraer `Validator` reutilizables

### Fase 3 — CDI
1. Agregar Weld Servlet al `pom.xml`
2. Agregar `beans.xml`
3. Anotar beans con CDI
4. Inyectar con `@Inject`

### Fase 4 — Dominio
1. Agregar reglas a `Member`, `Inventory`
2. Actualizar `LoanService` con transacciones
3. Tests unitarios de dominio

### Fase 5 — Cross-cutting
1. Agregar SLF4J + Logback
2. Reemplazar `printStackTrace`
3. Crear `SecurityHeadersFilter`
4. Configurar `<error-page>` en `web.xml`

## Criterio de éxito

Cada fase termina cuando:
- `./mvnw test` verde
- `./mvnw -DskipTests clean package` exitoso
- Los hallazgos de seguridad asociados están remediados (ver `docs/SECURITY_AUDIT.md`)
