# Librio — Arquitectura

> **Estado actual:** v4.4-public (baseline de auditoría)  
> **Estado objetivo:** post-refactor Fases 0–5 (ver `REFACTORING_ROADMAP.md`)

---

## Stack tecnológico

| Componente | Tecnología | Notas |
|-----------|-----------|-------|
| Lenguaje | Java 17 | `maven.compiler.release=17` |
| Runtime web | Jakarta Servlet 6.0 + JSP/JSTL | Tomcat 10.1 |
| Persistencia | JDBC puro | `mssql-jdbc 12.6.1` — **NO JPA** |
| Base de datos | SQL Server (Express en dev) | |
| Build | Maven Wrapper (`mvnw`) | Produce `LibraryMS.war` |
| Contenedor | Docker (Eclipse Temurin base) | Ver `Dockerfile` |
| Tests | JUnit 5.11 | Sin tests implementados aún |

> **Nota importante:** El `pom.xml` declara `jakarta.persistence-api` y `jakarta.ws.rs-api` pero ninguna entidad usa `@Entity` ni hay endpoints REST. Estas dependencias se eliminan en el refactor.

---

## Estructura de paquetes (estado actual)

```
dev.gustavorh.lms_dev_10/
├── application/
│   ├── dtos/          # MemberActivityReportDTO (solo reporte)
│   ├── factories/
│   │   ├── interfaces/    IRepositoryFactory, IServiceFactory
│   │   └── implementations/  JdbcRepositoryFactory, DefaultServiceFactory
│   └── services/
│       ├── interfaces/    IService<T>, IAuthService, IUserService
│       └── implementations/  AuthService, BookService, LoanService, ... (×11)
│
├── domain/
│   ├── entities/      Author, Book, Category, Inventory, Loan, Member,
│   │                  Permission, Role, RolePermissions, RoleUsers,
│   │                  Status, User
│   └── exceptions/    ServiceException
│
├── infrastructure/
│   ├── config/        DbContext (DriverManager — sin pool)
│   └── repositories/
│       ├── interfaces/    IRepository<T,ID>, IUserRepository
│       └── implementations/  JdbcAuthorRepository, JdbcBookRepository, ... (×12)
│
├── presentation/
│   ├── controllers/   AuthServlet, BookServlet, LoanServlet, ... (×11)
│   └── filters/       LoginFilter (auth only — sin roles), QueryFilter
│
└── utils/
    └── mappers/       IRowMapper<T>, AuthorMapper, BookMapper, ... (×12)
```

### Vistas JSP (`webapp/WEB-INF/views/`)

```
auth/          login.jsp
authors/       authors.jsp, form-author.jsp
books/         books.jsp, form-book.jsp
categories/    categories.jsp, form-category.jsp
loans/         loans.jsp, form-loan.jsp
members/       members.jsp, form-member.jsp
permissions/   permissions.jsp, form-permission.jsp
reports/       activity-report.jsp
role_permissions/  role_permissions.jsp, form-roles_permission.jsp
role_users/    role_users.jsp, form-role_user.jsp
roles/         roles.jsp, form-role.jsp
statuses/      statuses.jsp, form-status.jsp
users/         users.jsp, form-user.jsp
layouts/       header.jsp, footer.jsp
```

---

## Flujo de una request (estado actual)

```
HTTP Request
     │
     ▼
LoginFilter (/*) ──── no autenticado ──→ redirect /auth/login
     │
     │  autenticado
     ▼
[sin AuthorizationFilter — no hay control de roles]
     │
     ▼
*Servlet.init()
  └─ DbContext.getConnection() ← DriverManager (nueva conexión)
  └─ new JdbcRepositoryFactory(connection)
  └─ new DefaultServiceFactory(repositoryFactory)
     │
     ▼
*Servlet.doGet() / doPost()
  ├─ validate*(request)     ← validación inline
  ├─ build*(request)        ← construcción de entidad inline
  └─ service.operation()
       └─ Jdbc*Repository.*()
            └─ PreparedStatement (sobre la conexión de init)
     │
     ▼
JSP (forward) — datos en request attributes — ${entity.field} sin escapar
```

**Problemas del flujo actual:**
- La conexión se abre en `init()` y se comparte entre threads (no thread-safe).
- No hay pool; cada `init()` abre una nueva conexión permanente.
- No hay filtro de autorización por roles.
- Los JSPs renderizan datos sin escapar.

---

## Flujo objetivo (post-refactor)

```
HTTP Request
     │
     ▼
LoginFilter — verifica sesión activa
     │
     ▼
AuthorizationFilter — deny-by-default, verifica permisos desde sesión
     │
     ▼
SecurityHeadersFilter — agrega headers de seguridad
     │
     ▼
AppContextListener provee DataSource (HikariPool) y servicios (CDI)
     │
     ▼
CrudServlet<T> base (o concreto via CDI @Inject)
  └─ DataSource.getConnection()  ← del pool (thread-safe)
  └─ AbstractJdbcRepository<T>   ← genérico, sin boilerplate
  └─ Service.operation()
     │
     ▼
DTO → JSP (<c:out> escapado)
```

---

## Esquema de base de datos (resumen)

El DDL completo está en `DDL.sql`. El diccionario en `DiccionarioDatos.md`.

**Tablas principales:**

| Tabla | Descripción |
|-------|-------------|
| `Usuarios` | Usuarios del sistema (staff) |
| `Miembros` | Lectores/socios de la biblioteca |
| `Libros` | Catálogo de libros |
| `Autores` | Autores de libros |
| `Categorias` | Categorías de libros |
| `Prestamos` | Registro de préstamos |
| `Inventario` | Stock disponible por libro |
| `Roles` | Roles del sistema |
| `Permisos` | Permisos individuales |
| `Roles_Permisos` | Relación N:N roles↔permisos |
| `Roles_Usuarios` | Relación N:N roles↔usuarios |
| `Estados` | Catálogo de estados (activo/inactivo/etc.) |

**Nota de integridad:** actualmente `Inventario.cantidad_disponibles` no se decrementa al crear un préstamo ni se incrementa al devolverlo. Ver `REFACTORING_ROADMAP.md` Fase 4.

---

## Variables de entorno requeridas

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_URL` | JDBC connection string de SQL Server | `jdbc:sqlserver://db:1433;databaseName=LibraryDB;encrypt=true;trustServerCertificate=true` |
| `DB_USER` | Usuario de la base de datos | `sa` |
| `DB_PASSWORD` | Contraseña de la base de datos | *(ver `.env.example`)* |

Ver `.env.example` para una plantilla completa.

---

## Diagramas de referencia

- **ER Diagram:** `https://i.imgur.com/uS2HhBA.jpeg`
- **DDL:** `DDL.sql`
- **Datos de prueba:** `INSERTS.sql`
