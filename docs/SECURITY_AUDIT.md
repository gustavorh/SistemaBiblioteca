# Librio — Auditoría de Seguridad

> **Estado:** En progreso — SEC-01 a SEC-10 remediados; SEC-03, SEC-06 pendientes (roles y CSRF completados)  
> **Fecha de análisis:** 2026-05-28 · **Última actualización:** 2026-05-29  
> **Stack auditado:** Jakarta EE 10 · Servlet 6.0 · JSP/JSTL · JDBC puro · SQL Server · Tomcat 10.1  
> **Versión:** `LibraryMS 4.4-public`

---

## Lo que ya está bien

| Ítem | Evidencia |
|------|-----------|
| SQLi mitigado | `PreparedStatement` en todos los `Jdbc*Repository` |
| Credenciales por env vars | `DB_URL`, `DB_USER`, `DB_PASSWORD` — no hardcodeadas |
| Contenedor no-root | `Dockerfile` usa usuario de sistema sin privilegios |
| Driver actualizado | `mssql-jdbc 12.6.1` — sin CVE conocido al momento del análisis |

---

## Hallazgos priorizados

### Críticos

#### SEC-01 — Contraseñas en texto plano

| Campo | Detalle |
|-------|---------|
| **Severidad** | Crítica |
| **Ubicación** | `application/services/implementations/AuthService.java:27-30`; `JdbcUserRepository` (save/update) |
| **Descripción** | El login compara `user.getPassword().equals(password)` directamente. Las contraseñas se almacenan y comparan sin hash en ningún punto del flujo. |
| **Impacto** | Un dump de la base de datos expone todas las contraseñas en claro. |
| **Remediación** | Introducir una abstracción `PasswordEncoder` (interfaz) con implementación BCrypt o Argon2 (via `jBCrypt` o Spring Security Crypto standalone). Hash al crear/actualizar usuario; verificar en login. Migrar registros existentes. Nunca loguear passwords. |

#### SEC-02 — Contraseña renderizada en la UI

| Campo | Detalle |
|-------|---------|
| **Severidad** | Crítica |
| **Ubicación** | `webapp/WEB-INF/views/users/users.jsp` |
| **Descripción** | La vista lista usuarios con `${u.password}` en una columna de la tabla, exponiendo la contraseña (incluso si fuera hash, no debería mostrarse). |
| **Impacto** | Cualquier usuario con sesión iniciada ve las contraseñas de todos los usuarios. |
| **Remediación** | Eliminar la columna de la vista. Nunca incluir el campo `password` en objetos que se pasen a JSPs. |

---

### Altos

#### SEC-03 — Broken Access Control (sin control de roles)

| Campo | Detalle |
|-------|---------|
| **Severidad** | Alta |
| **Ubicación** | `presentation/filters/LoginFilter.java`; `presentation/controllers/RoleUsersServlet.java` |
| **Descripción** | `LoginFilter` solo verifica si existe sesión activa. El esquema de base de datos implementa Roles y Permisos (`Roles_Permisos`), pero esa información no se consulta en ningún filtro. Cualquier usuario autenticado puede acceder a `/users`, `/roles`, `/permissions` y auto-escalar privilegios. |
| **Impacto** | Escalada de privilegios horizontal y vertical. |
| **Remediación** | Crear un segundo filtro `AuthorizationFilter` (deny-by-default) que cargue los permisos del usuario desde `Roles_Permisos` en sesión y los compruebe contra la ruta solicitada. Mapeo permisos→rutas en un archivo de configuración o constantes. |

#### SEC-04 — Session fixation

| Campo | Detalle |
|-------|---------|
| **Severidad** | Alta |
| **Ubicación** | `presentation/controllers/AuthServlet.java:86-89` |
| **Descripción** | Tras un login exitoso el código reutiliza la sesión existente sin regenerar el ID (`req.getSession()` sin invalidar primero). Un atacante puede fijar el ID de sesión antes del login. |
| **Remediación** | Llamar `request.changeSessionId()` (Servlet 3.1+) o `session.invalidate()` + `request.getSession(true)` inmediatamente antes de escribir atributos de sesión. |

#### SEC-05 — Cookies de sesión sin flags de seguridad

| Campo | Detalle |
|-------|---------|
| **Severidad** | Alta |
| **Ubicación** | `WEB-INF/web.xml` (actualmente vacío de configuración de sesión) |
| **Descripción** | Las cookies de sesión de Tomcat no tienen `HttpOnly`, `Secure` ni `SameSite`. |
| **Remediación** | Agregar en `web.xml`: `<session-config><cookie-config><http-only>true</http-only><secure>true</secure></cookie-config></session-config>`. `SameSite=Lax` se configura en un filtro de respuesta o en `context.xml` de Tomcat. |

#### SEC-06 — Operaciones destructivas vía GET sin CSRF

| Campo | Detalle |
|-------|---------|
| **Severidad** | Alta |
| **Ubicación** | `presentation/controllers/*Servlet.java` (ej. `/users/delete?id=`) |
| **Descripción** | Los deletes se envían con GET. Sin CSRF, un enlace en cualquier sitio puede eliminar registros si el usuario tiene sesión activa. |
| **Remediación** | Mover todas las operaciones de mutación (create/update/delete) a POST/PUT/DELETE. Agregar token CSRF (synchronizer token pattern): generarlo en sesión, incluirlo como campo oculto en formularios, validarlo en el filtro antes de procesar. |

---

### Medios

#### SEC-07 — XSS reflejado/almacenado en vistas JSP

| Campo | Detalle |
|-------|---------|
| **Severidad** | Media |
| **Ubicación** | `WEB-INF/views/**/*.jsp` |
| **Descripción** | Las expresiones EL `${...}` no auto-escapan en JSP. Títulos, nombres, y cualquier dato de usuario renderizado directamente permite inyección de HTML/JS. |
| **Remediación** | Reemplazar `${campo}` por `<c:out value="${campo}"/>` o `${fn:escapeXml(campo)}` en todos los JSPs. Considerar una directiva global de escape. |

#### SEC-08 — Fuga de información por excepciones

| Campo | Detalle |
|-------|---------|
| **Severidad** | Media |
| **Ubicación** | `presentation/filters/QueryFilter.java`; múltiples servlets |
| **Descripción** | `e.printStackTrace()` y `resp.sendError(code, e.getMessage())` exponen stack traces y mensajes de excepción a la respuesta HTTP. |
| **Remediación** | Introducir SLF4J + Logback (o `java.util.logging`). Loguear internamente con `log.error("...", e)`. Responder con páginas de error genéricas configuradas en `web.xml` (`<error-page>`). |

#### SEC-09 — Validación de entrada insuficiente

| Campo | Detalle |
|-------|---------|
| **Severidad** | Media |
| **Ubicación** | `*Servlet.validate*`; todos los parsings de ID (`Long.valueOf(param)`) |
| **Descripción** | Solo se valida null/blank. Un parámetro no numérico en cualquier campo de ID lanza `NumberFormatException` no manejada. No hay validación de longitud, formato ni rango. |
| **Remediación** | Centralizar validación en clases `Validator` reutilizables. Usar `try/catch` o `Long.parseLong` con manejo de error en parsing de IDs. Considerar Hibernate Validator (Bean Validation) para reglas declarativas. |

---

### Bajos / Informativos

#### SEC-10 — LoginFilter bypass por variantes de URL

| Campo | Detalle |
|-------|---------|
| **Severidad** | Baja |
| **Ubicación** | `presentation/filters/LoginFilter.java:79-82` |
| **Descripción** | `PUBLIC_PATHS.stream().anyMatch(path::equals)` usa igualdad exacta. Una petición a `/auth/login/` (slash final) o `/auth/login;jsessionid=xxx` no coincide y puede comportarse inesperadamente. |
| **Remediación** | Normalizar la URI antes de comparar (strip de slash final, strip de `;jsessionid=...`). |

#### SEC-11 — Mass assignment en métodos `build*`

| Campo | Detalle |
|-------|---------|
| **Severidad** | Baja |
| **Ubicación** | `*Servlet.build*` en todos los servlets |
| **Descripción** | Los métodos `buildEntity` mapean todos los parámetros del request a la entidad. Un atacante puede enviar campos adicionales (ej. `roleId`, `status`) que serán procesados. |
| **Remediación** | Usar DTOs con campos explícitos en lugar de mapeo directo desde `request.getParameterMap()`. |

#### SEC-12 — Sin headers de seguridad HTTP

| Campo | Detalle |
|-------|---------|
| **Severidad** | Baja / Informativo |
| **Ubicación** | Global (no hay filtro de headers) |
| **Descripción** | Faltan `Content-Security-Policy`, `X-Frame-Options`, `X-Content-Type-Options`, `Referrer-Policy`, `HSTS`. |
| **Remediación** | Agregar un filtro `SecurityHeadersFilter` que añada estos headers a todas las respuestas. |

#### SEC-13 — `trustServerCertificate=true` en historial

| Campo | Detalle |
|-------|---------|
| **Severidad** | Baja / Informativo |
| **Ubicación** | Historial git / variables de entorno de desarrollo |
| **Descripción** | La opción `trustServerCertificate=true` en la connection string deshabilita la validación TLS del servidor SQL. |
| **Remediación** | En producción, usar un certificado válido y eliminar esta opción. Documentar que es solo para desarrollo local. |

#### SEC-14 — Dependencias de seguridad ausentes

| Campo | Detalle |
|-------|---------|
| **Severidad** | Informativo |
| **Ubicación** | `pom.xml` |
| **Descripción** | No hay librería de hashing de passwords, Bean Validation, ni SLF4J en el `pom.xml`. Las dependencias JPA y JAX-RS están declaradas pero no se usan en el código. |
| **Remediación** | Agregar `jBCrypt` o Spring Security Crypto (standalone), Hibernate Validator, SLF4J + Logback. Eliminar dependencias no usadas (JPA, JAX-RS) para reducir superficie. |

---

## Plan de remediation — Quick Wins

Orden sugerido (mayor impacto con menor esfuerzo):

```
SEC-02 → SEC-01 → SEC-04 → SEC-05 → SEC-08 → SEC-07 → SEC-06
```

1. **SEC-02**: Eliminar columna `${u.password}` en `users.jsp` — 5 minutos, riesgo cero.
2. **SEC-01**: Introducir `PasswordEncoder` + bcrypt — requiere migración de datos.
3. **SEC-04**: `request.changeSessionId()` tras login — 1 línea.
4. **SEC-05**: Configurar flags de cookie en `web.xml` — 4 líneas.
5. **SEC-08**: Agregar SLF4J + páginas de error genéricas.
6. **SEC-07**: Reemplazar `${...}` por `<c:out>` en JSPs.
7. **SEC-06**: Mover deletes a POST + token CSRF.

---

## Resumen ejecutivo

| Severidad | Cantidad |
|-----------|---------|
| Crítica | 2 |
| Alta | 4 |
| Media | 3 |
| Baja | 3 |
| Informativo | 2 |
| **Total** | **14** |

La vulnerabilidad más urgente es la combinación SEC-01 + SEC-02: contraseñas visibles en texto plano en la interfaz. La segunda prioridad es SEC-03 (ausencia total de control de acceso por roles), que invalida todo el esquema de permisos implementado en la base de datos.

Ver `docs/REFACTORING_ROADMAP.md` para el roadmap de código que acompaña estas correcciones.
