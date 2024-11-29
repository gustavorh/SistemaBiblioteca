/*

DELETE DATABASE

use master 
go
 alter database [Biblioteca] set single_user with rollback immediate

 drop database [Biblioteca]
 */

-- Creación de la base de datos
CREATE DATABASE Biblioteca;
GO

USE Biblioteca;
GO

-- Tabla Categorias
CREATE TABLE Categorias (
    id_categoria BIGINT IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL,
	CONSTRAINT PK_Categorias PRIMARY KEY (id_categoria)
);

-- Tabla Autores
CREATE TABLE Autores (
    id_autor BIGINT IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Autores PRIMARY KEY (id_autor)
);

-- Tabla Libros
CREATE TABLE Libros (
    id_libro BIGINT IDENTITY(1,1),
    titulo VARCHAR(100) NOT NULL,
    id_autor BIGINT NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    año_publicacion INT NOT NULL,
    id_categoria BIGINT NOT NULL,
	CONSTRAINT PK_Libros PRIMARY KEY (id_libro),
    CONSTRAINT FK_Libros_Autores FOREIGN KEY (id_autor) REFERENCES Autores(id_autor),
    CONSTRAINT FK_Libros_Categorias FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria)
);

-- Tabla Inventario
CREATE TABLE Inventario (
    id_libro BIGINT,
    cantidad_disponible INT NOT NULL,
	CONSTRAINT PK_Inventario PRIMARY KEY (id_libro),
    CONSTRAINT FK_Inventario_Libros FOREIGN KEY (id_libro) REFERENCES Libros(id_libro)
);

-- Tabla Estados
CREATE TABLE Estados (
    id_estado BIGINT IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NULL,
	CONSTRAINT PK_Estados PRIMARY KEY (id_estado)
);

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario BIGINT IDENTITY(1,1),
    usuario VARCHAR(20) NOT NULL,
    clave VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Usuarios PRIMARY KEY (id_usuario)
);

-- Tabla Miembros
CREATE TABLE Miembros (
    id_miembro BIGINT IDENTITY(1,1),
    RUT VARCHAR(10) NOT NULL UNIQUE,
    id_usuario BIGINT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NOT NULL,
    fecha_inscripcion DATETIME NOT NULL,
    id_estado BIGINT NOT NULL,
	CONSTRAINT PK_Miembros PRIMARY KEY (id_miembro),
    CONSTRAINT FK_Miembros_Usuarios FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    CONSTRAINT FK_Miembros_Estados FOREIGN KEY (id_estado) REFERENCES Estados(id_estado)
);

-- Tabla Prestamos
CREATE TABLE Prestamos (
    id_prestamo BIGINT IDENTITY(1,1),
    id_libro BIGINT NOT NULL,
    id_miembro BIGINT NOT NULL,
    fecha_desde DATETIME NOT NULL,
    fecha_hasta DATETIME NOT NULL,
    fecha_devolucion DATETIME NOT NULL,
	CONSTRAINT PK_Prestamos PRIMARY KEY (id_prestamo),
    CONSTRAINT FK_Prestamos_Libros FOREIGN KEY (id_libro) REFERENCES Libros(id_libro),
    CONSTRAINT FK_Prestamos_Miembros FOREIGN KEY (id_miembro) REFERENCES Miembros(id_miembro)
);

-- Tabla Roles
CREATE TABLE Roles (
    id_rol BIGINT IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NULL,
	CONSTRAINT PK_Roles PRIMARY KEY (id_rol)
);

-- Tabla Roles_Usuarios
CREATE TABLE Roles_Usuarios (
    id_rol BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    CONSTRAINT PK_RolesUsuarios PRIMARY KEY (id_rol, id_usuario),
    CONSTRAINT FK_Roles_Usuarios_Roles FOREIGN KEY (id_rol) REFERENCES Roles(id_rol),
    CONSTRAINT FK_Roles_Usuarios_Usuarios FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla Permisos
CREATE TABLE Permisos (
    id_permiso BIGINT IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NULL
	CONSTRAINT PK_Permisos PRIMARY KEY (id_permiso)
);

-- Tabla Roles_Permisos
CREATE TABLE Roles_Permisos (
    id_rol BIGINT NOT NULL,
    id_permiso BIGINT NOT NULL,
    PRIMARY KEY (id_rol, id_permiso),
    CONSTRAINT FK_Roles_Permisos_Roles FOREIGN KEY (id_rol) REFERENCES Roles(id_rol),
    CONSTRAINT FK_Roles_Permisos_Permisos FOREIGN KEY (id_permiso) REFERENCES Permisos(id_permiso)
);
