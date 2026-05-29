# Librio — Roadmap de Refactor SOLID/DRY/Patrones

> **Estado:** Planificado — pendiente de ejecución  
> **Fecha:** 2026-05-28  
> **Criterio:** Cada fase es autocontenida y ejecutable en una sesión independiente. La Fase 0 es prerequisito de todas las demás.

---

## Problemas identificados en el código actual

| ID | Categoría | Descripción | Archivos afectados |
|----|-----------|-------------|-------------------|
| A1 | **DRY** | CRUD idéntico repetido en 12 `Jdbc*Repository` y 11 `*Servlet` | `infrastructure/repositories/implementations/*`, `presentation/controllers/*` |
| A2 | **DI ausente** | Cada servlet/filtro hace `new JdbcRepositoryFactory(connection)` + `new DefaultServiceFactory(factory)` en `init()` | Todos los servlets y `LoginFilter` |
| A3 | **Conexiones sin pool** | `DriverManager.getConnection()` por request; conexión retenida en `init()` (no thread-safe); segunda conexión en `QueryFilter` | `DbContext.java`, `QueryFilter.java` |
| A4 | **SRP violado** | Servlets mezclan routing, validación (`validate*`), construcción de entidad (`build*`) y dispatch | `UserServlet.java`, `BookServlet.java`, todos |
| A5 | **Dominio anémico** | Entidades sin reglas; sin decremento de inventario en préstamo; sin validación de elegibilidad de miembro | `domain/entities/*`, `LoanService.java` |
| A6 | **Dependencias no usadas** | JPA y JAX-RS declaradas en `pom.xml` pero ninguna entidad tiene `@Entity` ni hay endpoints REST | `pom.xml` vs `domain/entities/*` |

---

## Fase 0 — Red de seguridad (Esfuerzo: S)

**Objetivo:** crear la base de testing y calidad antes de cualquier cambio en el código de negocio.

### Tareas

- [ ] Crear `src/test/java/dev/gustavorh/lms_dev_10/` con estructura espejo.
- [ ] Agregar al `pom.xml`: H2 (scope test) o Testcontainers + SQL Server para tests de integración.
- [ ] Escribir al menos un test de "smoke" por repositorio para verificar que el CRUD funciona antes y después del refactor.
- [ ] Agregar `.editorconfig` y Spotless/Checkstyle (ya existe en raíz).
- [ ] Crear `.github/workflows/ci.yml` con job: checkout → setup JDK 17 → `./mvnw -DskipTests clean package` → `./mvnw test`.
- [ ] Verificar que `./mvnw -DskipTests clean package` produce `target/LibraryMS.war` limpio.

### Criterio de completitud
`./mvnw test` verde en CI con al menos un test de repo por entidad.

---

## Fase 1 — Cimiento de persistencia (Esfuerzo: M)

**Objetivo:** eliminar conexiones por request, centralizar el pool, y crear una base genérica de repositorio que elimine el CRUD ×12.

### Tareas

- [ ] Agregar `HikariCP` al `pom.xml`.
- [ ] Refactorizar `DbContext` para exponer un `DataSource` (HikariPool) en lugar de `DriverManager.getConnection()`.
- [ ] Crear `AbstractJdbcRepository<T, ID>` con:
  - `findAll()`: `SELECT *` + mapper
  - `findById(ID id)`: `SELECT * WHERE id = ?` + mapper
  - `save(T entity)`: INSERT genérico basado en metadata de tabla
  - `update(T entity)`: UPDATE genérico
  - `delete(ID id)`: DELETE genérico
  - Recibe `IRowMapper<T>` y metadata (tabla, columnas, PK) en constructor.
- [ ] Migrar los 12 `Jdbc*Repository` para extender `AbstractJdbcRepository` (eliminar CRUD duplicado, conservar solo queries especializadas).
- [ ] Quitar la conexión retenida en `init()` de servlets y filtros — obtener del pool por operación.
- [ ] Eliminar `QueryFilter.java` (conexión extra) o refactorizarlo para usar el pool.

### Criterio de completitud
Todos los tests de Fase 0 siguen verdes. Build limpio. Sin `DriverManager.getConnection()` fuera de `DbContext`.

### Arregla
A1 (parcial), A3, DIP en repositorios.

---

## Fase 2 — De-duplicar capa web (Esfuerzo: M/L)

**Objetivo:** eliminar el boilerplate ×11 en servlets y centralizar el wiring de dependencias.

### Tareas

- [ ] Crear `CrudServlet<T>` base (o un dispatcher front-controller) que implemente el patrón GET-list/GET-form/POST-save/POST-delete compartido.
- [ ] Migrar los 11 `*Servlet` concretos para extender `CrudServlet<T>` (solo override de lógica específica).
- [ ] Mover el wiring de factories (`JdbcRepositoryFactory`, `DefaultServiceFactory`) a un `AppContextListener implements ServletContextListener`, guardando las factories como atributos del `ServletContext` (app-scoped, thread-safe).
- [ ] Crear DTOs de entrada para cada entidad (ej. `CreateUserDTO`, `UpdateBookDTO`) y un `DtoMapper` que los convierta a/desde entidades — así los JSPs y servlets nunca reciben `User` directamente.
- [ ] Extraer `Validator` reutilizables (ej. `IdValidator.parseLong(param)`) para reemplazar los `validate*` inline.
- [ ] Definir constantes para rutas JSP y redirects (no más strings mágicos en servlets).

### Criterio de completitud
Todos los tests verdes. Sin `new JdbcRepositoryFactory` ni `new DefaultServiceFactory` dentro de servlets. Sin `Long.valueOf` desnudo en servlets.

### Arregla
A1 (completado), A2 (parcial), A4, SEC-09, SEC-11, magic strings.

---

## Fase 3 — Inyección de dependencias (Esfuerzo: M)

**Objetivo:** reemplazar factories manuales con CDI (Weld) para desacoplar completamente la construcción de objetos.

### Tareas

- [ ] Agregar `weld-servlet-shaded` al `pom.xml`.
- [ ] Agregar `beans.xml` en `WEB-INF/` para activar CDI.
- [ ] Anotar repositorios con `@ApplicationScoped`, servicios con `@RequestScoped` o `@ApplicationScoped` según corresponda.
- [ ] Inyectar con `@Inject` en servlets y filtros (reemplazar `init()` manual).
- [ ] Eliminar `IRepositoryFactory` y `IServiceFactory` (reemplazados por CDI producer si se necesita customización).
- [ ] Definir interfaces de servicio para cada servicio que aún no tenga una (no solo `IService<T>` genérico).

### Criterio de completitud
Todos los tests verdes. Sin `new` de factories en servlets/filtros. Contenedor CDI arranca con la app.

### Arregla
A2 (completado), ISP (interfaces de servicio), DIP, testabilidad.

---

## Fase 4 — Integridad de dominio (Esfuerzo: M)

**Objetivo:** llevar las reglas de negocio al dominio y a los servicios; eliminar entidades anémicas.

### Tareas

- [ ] `Member`: agregar método `isEligibleForLoan()` que verifique `estado = Activo`.
- [ ] `LoanService.createLoan()`: verificar elegibilidad del miembro antes de crear el préstamo.
- [ ] `Inventory`: agregar `decreaseAvailable()` y `increaseAvailable()` con invariante (`cantidad_disponibles >= 0`).
- [ ] `LoanService.createLoan()` y `returnLoan()`: llamar a `Inventory.decreaseAvailable()` / `increaseAvailable()` dentro de una transacción (un `UnitOfWork` o `Connection` compartida).
- [ ] Agregar lógica de cálculo de atraso en `Loan` o en `LoanService`.
- [ ] Validar formato de RUT en `Member` (o en su DTO de entrada).
- [ ] Escribir tests unitarios para cada regla de dominio.

### Criterio de completitud
Tests verdes incluyendo casos de borde: préstamo con miembro inactivo rechazado; stock nunca negativo; devolución incrementa stock.

### Arregla
A5, consistencia transaccional préstamo↔inventario.

---

## Fase 5 — Cross-cutting concerns (Esfuerzo: S/M)

**Objetivo:** logging estructurado, manejo de errores, y headers de seguridad.

### Tareas

- [ ] Agregar SLF4J API + Logback al `pom.xml`.
- [ ] Reemplazar todos los `e.printStackTrace()` y `System.out.println()` por `log.error(...)` / `log.info(...)`.
- [ ] Reemplazar `resp.sendError(code, e.getMessage())` por páginas de error configuradas en `web.xml` (`<error-page>`).
- [ ] Crear `SecurityHeadersFilter` que agregue en cada respuesta:
  - `X-Content-Type-Options: nosniff`
  - `X-Frame-Options: DENY`
  - `Referrer-Policy: no-referrer`
  - `Content-Security-Policy: default-src 'self'` (ajustar según recursos externos)
- [ ] Configurar `logback.xml` con appender de archivo rotativo.

### Criterio de completitud
Cero `printStackTrace()` en el código. Headers de seguridad presentes en todas las respuestas. Tests verdes.

### Arregla
SEC-08, SEC-12.

---

## Fixes de seguridad independientes (ejecutar en cualquier momento post Fase 0)

Estos no dependen del roadmap de refactor y pueden aplicarse antes o durante las fases:

| Fix | Referencia | Estimado |
|-----|-----------|----------|
| Eliminar `${u.password}` de `users.jsp` | SEC-02 | 2 min |
| `request.changeSessionId()` en `AuthServlet` | SEC-04 | 5 min |
| Flags `HttpOnly`/`Secure` en `web.xml` | SEC-05 | 5 min |
| Normalizar URI en `LoginFilter` | SEC-10 | 10 min |
| Introducir `PasswordEncoder` + bcrypt | SEC-01 | 1-2 h (+ migración de datos) |
| Mover deletes a POST + token CSRF | SEC-06 | 2-4 h |
| `<c:out>` en todos los JSPs | SEC-07 | 1-2 h |
| Filtro de autorización por roles | SEC-03 | 4-8 h |

---

## Verificación final (post todas las fases)

- `./mvnw test` verde (CI).
- Login funciona con claves hasheadas (migración aplicada).
- Usuario no-admin bloqueado al intentar acceder a `/users` y `/roles`.
- El ID de sesión cambia tras login (verificar con DevTools de navegador).
- `${u.password}` no aparece en ninguna vista (grep en `webapp/`).
- DELETE de entidades exige POST + token CSRF.
- Todas las respuestas incluyen headers de seguridad (verificar con `curl -I`).
- `docker-compose up` levanta la app; login y CRUD funcionan.

---

## Dependencias nuevas requeridas (resumen)

```xml
<!-- Fase 0 -->
<dependency><!-- H2 o Testcontainers --></dependency>
<!-- Fase 1 -->
<dependency><!-- HikariCP --></dependency>
<!-- Fase 3 -->
<dependency><!-- Weld Servlet --></dependency>
<!-- Fase 5 + Seguridad -->
<dependency><!-- SLF4J API + Logback --></dependency>
<dependency><!-- jBCrypt o Spring Security Crypto --></dependency>
<!-- Validación -->
<dependency><!-- Hibernate Validator --></dependency>
```

Ver `docs/SECURITY_AUDIT.md` para el detalle completo de cada vulnerabilidad.
