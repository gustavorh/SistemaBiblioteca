# /test — Ejecutar tests de Librio

Ejecuta el suite de tests con JUnit 5.

```bash
./mvnw test
```

Para tests de un módulo específico:
```bash
./mvnw test -Dtest=NombreDeClaseTest
```

Para tests con reporte detallado:
```bash
./mvnw test -Dsurefire.failIfNoSpecifiedTests=false
```

**Nota:** Antes de la Fase 0 del roadmap no hay tests implementados. El comando anterior retornará "No tests to run" — eso es esperado.

Ver `docs/REFACTORING_ROADMAP.md` Fase 0 para el plan de setup de tests.
