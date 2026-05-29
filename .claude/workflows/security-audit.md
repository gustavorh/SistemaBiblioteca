# Workflow: security-audit

Re-ejecuta la auditoría de seguridad completa del proyecto Librio y actualiza `docs/SECURITY_AUDIT.md`.

## Cuándo usar

- Antes de cada PR/merge importante.
- Tras aplicar fixes de seguridad (para verificar que los hallazgos están cerrados).
- Al inicio de una nueva sesión de trabajo en el área de seguridad.

## Fases del workflow

### Fase 1 — Verificar quick wins

Comprobar el estado de los hallazgos críticos y altos:

```bash
# SEC-01/02: contraseñas
grep -rn "getPassword\|\.password\b" src/main/java/dev/gustavorh/lms_dev_10/application/services/
grep -n 'u\.password\|user\.password' src/main/webapp/WEB-INF/views/users/users.jsp

# SEC-03: control de roles en LoginFilter
grep -n "permission\|role\|Roles_Permisos" src/main/java/dev/gustavorh/lms_dev_10/presentation/filters/LoginFilter.java

# SEC-04: session fixation
grep -n "changeSessionId\|invalidate" src/main/java/dev/gustavorh/lms_dev_10/presentation/controllers/AuthServlet.java

# SEC-05: cookie flags
grep -n "HttpOnly\|Secure\|SameSite\|cookie-config" src/main/webapp/WEB-INF/web.xml

# SEC-06: deletes vía GET
grep -rn "delete" src/main/java/dev/gustavorh/lms_dev_10/presentation/controllers/ | grep "doGet"

# SEC-07: XSS sin escaping
grep -rn '\${\w' src/main/webapp/WEB-INF/views/ | grep -v 'c:out\|fn:escapeXml' | wc -l

# SEC-08: stack traces
grep -rn "printStackTrace" src/main/java/

# SEC-10: URL bypass
grep -n "equals\|isPublicPath" src/main/java/dev/gustavorh/lms_dev_10/presentation/filters/LoginFilter.java
```

### Fase 2 — Verificar dependencias

```bash
# Listar dependencias con versiones
./mvnw dependency:list -DincludeScope=compile

# Verificar que JPA/JAX-RS no usadas se eliminaron (post-refactor)
grep -rn "@Entity\|@Path\|@GET\|@POST" src/main/java/
```

### Fase 3 — Actualizar documento

1. Leer `docs/SECURITY_AUDIT.md`.
2. Para cada hallazgo corregido, agregar al final de su sección: `**Resuelto:** YYYY-MM-DD — <descripción del fix>`.
3. Si se encuentran hallazgos nuevos, agregarlos con severidad y remediación.
4. Actualizar la tabla resumen al final del documento.

## Output esperado

- `docs/SECURITY_AUDIT.md` actualizado con estados y fechas de resolución.
- Lista de hallazgos abiertos que quedan por remediar.
- Lista de hallazgos cerrados en esta ejecución.
