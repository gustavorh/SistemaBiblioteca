# Librio — Guía para Claude Code

## Visión general

**Librio** es un sistema de gestión de biblioteca universitaria. Stack: **Jakarta EE 10** (Servlet 6.0 + JSP/JSTL), **JDBC puro sobre SQL Server**, empaquetado **WAR** sobre **Tomcat 10.1**, contenedorizado con Docker.

- `groupId`: `dev.gustavorh` · `artifactId`: `LibraryMS` · `version`: `4.4-public`
- Paquete raíz: `dev.gustavorh.lms_dev_10`
- WAR final: `target/LibraryMS.war`

## Comandos esenciales

```bash
# Build (sin tests)
./mvnw -DskipTests clean package

# Build + tests
./mvnw clean verify

# Levantar con Docker Compose (app + SQL Server)
docker-compose up --build

# Solo levantar la DB
docker-compose up db

# Build de imagen Docker manualmente
docker build -t librio:local .
```

## Arquitectura en capas

```
presentation/  → Servlets + JSPs + Filtros
application/   → Servicios + Factories + DTOs
domain/        → Entidades + Excepciones (sin anotaciones JPA)
infrastructure/→ JDBC: DbContext + Jdbc*Repository + IRowMapper
utils/mappers/ → ResultSet → Entidad
```

Ver `docs/ARCHITECTURE.md` para el flujo completo de una request y el esquema de BD.

## Variables de entorno requeridas

| Variable | Descripción |
|----------|-------------|
| `DB_URL` | JDBC URL de SQL Server (ej. `jdbc:sqlserver://db:1433;databaseName=LibraryDB;encrypt=true;trustServerCertificate=true`) |
| `DB_USER` | Usuario SQL Server |
| `DB_PASSWORD` | Contraseña SQL Server |

Copiar `.env.example` → `.env` y completar antes de ejecutar.

## Gotchas importantes

- **JDBC puro — NO JPA.** Las entidades en `domain/entities/` son POJOs simples, sin `@Entity`. El `pom.xml` declara `jakarta.persistence-api` pero no se usa.
- **Sin pool de conexiones.** `DbContext.getConnection()` usa `DriverManager` directo. Cada servlet abre una conexión en `init()` y la mantiene (no thread-safe). Fix planificado en Fase 1 del roadmap.
- **Conexión no thread-safe en servlets.** La conexión JDBC almacenada en campo del servlet es compartida entre threads concurrentes. No agregar lógica que dependa de estado por-request en esa conexión.
- **Sin DI real.** Las factories se instancian manualmente en `init()` de cada servlet y filtro. CDI planificado en Fase 3.
- **BCrypt activo** (SEC-01 resuelto) — `UserService.save/update` hashea con `BCryptPasswordEncoder`. El login usa `passwordEncoder.matches()`. Las contraseñas existentes en DB son texto plano; re-registrar usuarios para que funcione el login.
- **Sin control de acceso por roles** (SEC-03 pendiente) — `LoginFilter` solo verifica sesión activa. Cualquier usuario autenticado puede acceder a todas las rutas.
- **`trustServerCertificate=true`** — solo válido en desarrollo local. Nunca en producción.

## Zonas sensibles de seguridad

| Archivo | Riesgo | Estado |
|---------|--------|--------|
| `application/services/implementations/UserService.java` | Hash BCrypt en save/update — passwords pre-existentes en DB son texto plano | ✅ SEC-01 resuelto |
| `presentation/filters/LoginFilter.java` | Sin autorización por roles — acceso total con sesión activa | ⚠️ SEC-03 pendiente |
| `presentation/filters/CsrfFilter.java` | Token CSRF sincrónico — validado en todos los POST | ✅ SEC-06 resuelto |
| `presentation/controllers/AuthServlet.java` | `changeSessionId()` ya presente | ✅ SEC-04 resuelto |
| `infrastructure/config/DbContext.java` | Sin pool — no escala | ⚠️ Fase 1 roadmap |

## Documentación clave

| Documento | Descripción |
|-----------|-------------|
| `docs/SECURITY_AUDIT.md` | 14 hallazgos de seguridad priorizados |
| `docs/REFACTORING_ROADMAP.md` | 6 fases de refactor SOLID/DRY |
| `docs/ARCHITECTURE.md` | Arquitectura actual y objetivo |
| `DiccionarioDatos.md` | Diccionario de la base de datos |
| `DDL.sql` | Script de creación de esquema |
| `INSERTS.sql` | Datos iniciales de prueba |

## Comandos personalizados disponibles

- `/build` — compilar y empaquetar
- `/test` — ejecutar tests
- `/audit` — re-ejecutar auditoría de seguridad
- `/refactor-phase` — ejecutar una fase del roadmap de refactor
