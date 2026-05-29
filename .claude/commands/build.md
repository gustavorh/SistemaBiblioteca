# /build — Compilar y empaquetar Librio

Compila el proyecto y genera `target/LibraryMS.war`.

```bash
./mvnw -DskipTests clean package
```

Si el build falla, revisa:
1. `pom.xml` — dependencias y versiones
2. Errores de compilación Java en `src/main/java/`
3. Que el `JDK 17` esté disponible (`java -version`)

Para incluir tests en el build:
```bash
./mvnw clean verify
```
