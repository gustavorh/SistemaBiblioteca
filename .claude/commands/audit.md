# /audit — Auditoría de seguridad de Librio

Re-ejecuta un análisis de seguridad del código actual y compara con los hallazgos base en `docs/SECURITY_AUDIT.md`.

## Qué revisar

1. **SEC-01/02 — Contraseñas:** ¿Sigue habiendo comparación en texto plano en `AuthService`? ¿Sigue `${u.password}` en `users.jsp`?

```bash
grep -r "getPassword" src/main/java/
grep -r "u.password\|user.password" src/main/webapp/
```

2. **SEC-03 — Control de roles:** ¿El `LoginFilter` verifica permisos además de sesión?

```bash
grep -n "Roles_Permisos\|permission\|role" src/main/java/dev/gustavorh/lms_dev_10/presentation/filters/LoginFilter.java
```

3. **SEC-04 — Session fixation:** ¿Se llama `changeSessionId()` o se invalida sesión antes de login?

```bash
grep -n "changeSessionId\|invalidate\|getSession" src/main/java/dev/gustavorh/lms_dev_10/presentation/controllers/AuthServlet.java
```

4. **SEC-06 — GET para deletes:** ¿Los deletes siguen siendo GET?

```bash
grep -rn "delete" src/main/java/dev/gustavorh/lms_dev_10/presentation/controllers/ | grep -i "doGet\|GET"
```

5. **SEC-07 — XSS:** ¿Siguen habiendo `${...}` sin `<c:out>` en JSPs?

```bash
grep -rn '\${' src/main/webapp/WEB-INF/views/ | grep -v 'c:out\|fn:escapeXml'
```

6. **SEC-08 — printStackTrace:** ¿Quedan stack traces expuestos?

```bash
grep -rn "printStackTrace\|sendError.*getMessage" src/main/java/
```

## Actualizar el documento

Tras revisar, actualizar `docs/SECURITY_AUDIT.md`:
- Cambiar estado de hallazgos corregidos a "Resuelto" con la fecha.
- Agregar nuevos hallazgos si se detectan.
