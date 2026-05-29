# Librio — Sistema de Gestión de Biblioteca

**Librio** es un sistema de gestión de biblioteca universitaria construido como proyecto de portafolio. Gestiona libros, autores, categorías, socios, préstamos, inventario, usuarios y control de roles/permisos.

> **Estado de seguridad:** Ver `docs/SECURITY_AUDIT.md` para los hallazgos auditados y su prioridad de remediación.  
> **Roadmap de refactor:** Ver `docs/REFACTORING_ROADMAP.md` (Fases 0–5, SOLID/DRY/patrones).

---

## Stack tecnológico

| Componente | Tecnología |
|-----------|-----------|
| Lenguaje | Java 17 |
| Runtime web | Jakarta EE 10 — Servlet 6.0 + JSP/JSTL |
| Persistencia | **JDBC puro** sobre SQL Server (`mssql-jdbc 12.6.1`) |
| Servidor | Apache Tomcat 10.1 |
| Empaquetado | WAR (`target/LibraryMS.war`) |
| Build | Maven Wrapper (`./mvnw`) |
| Contenedor | Docker (Eclipse Temurin 17) |

> **Nota:** el proyecto usa JDBC puro. No hay JPA ni `@Entity`. Las dependencias `jakarta.persistence-api` y `jakarta.ws.rs-api` en el `pom.xml` son legado — se eliminan en el refactor.

---

## Arquitectura

Arquitectura en capas (Layered Architecture) con separación presentation / application / domain / infrastructure:

```
presentation/  → Servlets (Jakarta EE) + JSPs + Filtros
application/   → Servicios de negocio + Abstract Factory + DTOs
domain/        → Entidades (POJOs) + Excepciones de dominio
infrastructure/→ JDBC: DbContext + Jdbc*Repository + IRowMapper
utils/mappers/ → Mapeo ResultSet → Entidad
```

**Patrones aplicados:** Repository, Abstract Factory, Facade (interfaces de servicio), RowMapper.

Ver `docs/ARCHITECTURE.md` para el flujo completo de una request, el mapa de paquetes y el esquema de BD.

---

## Variables de entorno

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_URL` | JDBC connection string SQL Server | `jdbc:sqlserver://db:1433;databaseName=LibraryDB;encrypt=true;trustServerCertificate=true` |
| `DB_USER` | Usuario de BD | `sa` |
| `DB_PASSWORD` | Contraseña de BD | *(ver `.env.example`)* |

Copiar `.env.example` → `.env` y completar antes de ejecutar.

---

## Inicio rápido con Docker Compose

```bash
# 1. Configurar variables de entorno
cp .env.example .env
# Editar .env con tu contraseña

# 2. Levantar SQL Server + app
docker-compose up --build

# 3. Abrir en el navegador
open http://localhost:8080/LibraryMS
```

El `docker-compose.yml` levanta SQL Server 2022 Express y la aplicación Tomcat. Esperar ~30 segundos a que SQL Server esté healthy antes de que la app conecte.

**Después de levantar la BD por primera vez:**

```bash
# Ejecutar DDL (crea el esquema)
docker exec -i <contenedor_db> /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "$DB_PASSWORD" -i /dev/stdin < DDL.sql

# Cargar datos iniciales
docker exec -i <contenedor_db> /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "$DB_PASSWORD" -i /dev/stdin < INSERTS.sql
```

---

## Desarrollo local (sin Docker)

### Prerequisitos

- JDK 17
- SQL Server (local o Docker)
- Apache Tomcat 10.1

### Build

```bash
# Compilar y empaquetar (sin tests)
./mvnw -DskipTests clean package

# Con tests (cuando estén implementados — ver Fase 0 del roadmap)
./mvnw clean verify
```

El WAR se genera en `target/LibraryMS.war`. Desplegarlo en Tomcat o configurar una run configuration en IntelliJ IDEA apuntando al WAR.

### Configurar Tomcat en IntelliJ IDEA

1. `Run > Edit Configurations > Add New > Tomcat Server (Local)`
2. En `Application Server`, apuntar al directorio de instalación de Tomcat 10.1.
3. En la pestaña `Deployment`, agregar el artefacto `LibraryMS.war`.
4. Configurar las variables de entorno `DB_URL`, `DB_USER`, `DB_PASSWORD`.
5. Ejecutar.

---

## Documentación

| Documento | Descripción |
|-----------|-------------|
| `docs/SECURITY_AUDIT.md` | 14 hallazgos de seguridad priorizados + plan de remediación |
| `docs/REFACTORING_ROADMAP.md` | Roadmap de 6 fases SOLID/DRY + dependencias nuevas requeridas |
| `docs/ARCHITECTURE.md` | Arquitectura actual y objetivo, flujo de requests, esquema de BD |
| `DiccionarioDatos.md` | Diccionario de datos de la base de datos |
| `DDL.sql` | Script de creación de esquema SQL Server |
| `INSERTS.sql` | Datos iniciales de prueba |

---

## Diagrama ER

![Diagrama ER](https://i.imgur.com/uS2HhBA.jpeg)

---

## Contribución

Pull requests bienvenidos. Ver `docs/REFACTORING_ROADMAP.md` para las áreas prioritarias de trabajo.

---

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).
