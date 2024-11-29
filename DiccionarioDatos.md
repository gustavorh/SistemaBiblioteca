
# Diccionario de Datos - Sistema de Biblioteca

## 1. Tabla: Categorias

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_categoria | BIGINT | Identificador único de categoría | PK, Identity(1,1) |
| nombre | VARCHAR(50) | Nombre de la categoría | No nulo |

## 2. Tabla: Autores

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_autor | BIGINT | Identificador único de autor | PK, Identity(1,1) |
| nombre | VARCHAR(100) | Nombre del autor | No nulo |
| apellido | VARCHAR(100) | Apellido del autor | No nulo |
| nombre_completo | VARCHAR(100) | Nombre completo del autor | No nulo |

## 3. Tabla: Libros

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_libro | BIGINT | Identificador único de libro | PK, Identity(1,1) |
| titulo | VARCHAR(100) | Título del libro | No nulo |
| id_autor | BIGINT | ID del autor del libro | No nulo, FK Autores |
| isbn | VARCHAR(13) | Código ISBN del libro | No nulo |
| año_publicacion | INT | Año de publicación del libro | No nulo |
| id_categoria | BIGINT | ID de la categoría del libro | No nulo, FK Categorias |

## 4. Tabla: Inventario

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_libro | BIGINT | Identificador del libro | PK, FK Libros |
| cantidad_disponible | INT | Número de copias disponibles | No nulo |

## 5. Tabla: Estados

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_estado | BIGINT | Identificador único de estado | PK, Identity(1,1) |
| nombre | VARCHAR(50) | Nombre del estado | No nulo |
| descripcion | VARCHAR(100) | Descripción del estado | Nullable |

## 6. Tabla: Usuarios

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_usuario | BIGINT | Identificador único de usuario | PK, Identity(1,1) |
| usuario | VARCHAR(20) | Nombre de usuario | No nulo |
| clave | VARCHAR(100) | Contraseña del usuario | No nulo |

## 7. Tabla: Miembros

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_miembro | BIGINT | Identificador único de miembro | PK, Identity(1,1) |
| RUT | VARCHAR(10) | RUT del miembro | No nulo, Único |
| id_usuario | BIGINT | ID del usuario asociado | No nulo, FK Usuarios |
| nombre | VARCHAR(50) | Nombre del miembro | No nulo |
| apellido_paterno | VARCHAR(100) | Apellido paterno del miembro | No nulo |
| apellido_materno | VARCHAR(100) | Apellido materno del miembro | No nulo |
| fecha_inscripcion | DATETIME | Fecha de inscripción del miembro | No nulo |
| id_estado | BIGINT | ID del estado del miembro | No nulo, FK Estados |

## 8. Tabla: Prestamos

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_prestamo | BIGINT | Identificador único de préstamo | PK, Identity(1,1) |
| id_libro | BIGINT | ID del libro prestado | No nulo, FK Libros |
| id_miembro | BIGINT | ID del miembro que realiza préstamo | No nulo, FK Miembros |
| fecha_desde | DATETIME | Fecha de inicio del préstamo | No nulo |
| fecha_hasta | DATETIME | Fecha de devolución prevista | No nulo |
| fecha_devolucion | DATETIME | Fecha real de devolución | No nulo |

## 9. Tabla: Roles

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_rol | BIGINT | Identificador único de rol | PK, Identity(1,1) |
| nombre | VARCHAR(50) | Nombre del rol | No nulo |
| descripcion | VARCHAR(100) | Descripción del rol | Nullable |

## 10. Tabla: Roles_Usuarios

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_rol | BIGINT | ID del rol | PK, FK Roles |
| id_usuario | BIGINT | ID del usuario | PK, FK Usuarios |

## 11. Tabla: Permisos

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_permiso | BIGINT | Identificador único de permiso | PK, Identity(1,1) |
| nombre | VARCHAR(100) | Nombre del permiso | No nulo |
| descripcion | VARCHAR(100) | Descripción del permiso | Nullable |

## 12. Tabla: Roles_Permisos

| Columna | Tipo | Descripción | Restricciones |
|---------|------|-------------|---------------|
| id_rol | BIGINT | ID del rol | PK, FK Roles |
| id_permiso | BIGINT | ID del permiso | PK, FK Permisos |

**Leyenda de Restricciones:**
- *PK*: Primary Key (Clave Primaria)
- *FK*: Foreign Key (Clave Foránea)
- *Identity(1,1)*: Incremento automático
- *Nullable*: Permite valores nulos
