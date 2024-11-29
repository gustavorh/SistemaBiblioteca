-- Insertar Categorias (10 records)
INSERT INTO Categorias (nombre) VALUES 
('Ficci�n'),
('No Ficci�n'),
('Ciencia'),
('Historia'),
('Literatura Cl�sica'),
('Poes�a'),
('Biograf�as'),
('Tecnolog�a'),
('Arte'),
('Filosof�a');

-- Insertar Autores (10 records)
INSERT INTO Autores (nombre, apellido, nombre_completo) VALUES
('Gabriel', 'Garc�a M�rquez', 'Gabriel Garc�a M�rquez'),
('Isabel', 'Allende', 'Isabel Allende'),
('Pablo', 'Neruda', 'Pablo Neruda'),
('Jorge Luis', 'Borges', 'Jorge Luis Borges'),
('Mario', 'Vargas Llosa', 'Mario Vargas Llosa'),
('Julio', 'Cort�zar', 'Julio Cort�zar'),
('Octavio', 'Paz', 'Octavio Paz'),
('Miguel', 'de Cervantes', 'Miguel de Cervantes'),
('Federico', 'Garc�a Lorca', 'Federico Garc�a Lorca'),
('Carlos', 'Ruiz Zaf�n', 'Carlos Ruiz Zaf�n');

-- Insertar Libros (10 records)
INSERT INTO Libros (titulo, id_autor, isbn, a�o_publicacion, id_categoria) VALUES
('Cien a�os de soledad', 1, '9780307474728', 1967, 1),
('La casa de los esp�ritus', 2, '9780525433477', 1982, 1),
('Veinte poemas de amor', 3, '9780307477927', 1924, 6),
('El Aleph', 4, '9780142437883', 1949, 1),
('La ciudad y los perros', 5, '9780312471279', 1963, 1),
('Rayuela', 6, '9780394752846', 1963, 1),
('El laberinto de la soledad', 7, '9780802150424', 1950, 2),
('Don Quijote de la Mancha', 8, '9788424922498', 1605, 5),
('Romancero gitano', 9, '9780811222730', 1928, 6),
('La sombra del viento', 10, '9780143034902', 2001, 1);

-- Insertar Estados (5 records)
INSERT INTO Estados (nombre, descripcion) VALUES
('Activo', 'Usuario con todos los privilegios'),
('Inactivo', 'Usuario temporalmente suspendido'),
('Pendiente', 'Usuario en proceso de validaci�n'),
('Bloqueado', 'Usuario bloqueado por infracciones'),
('Eliminado', 'Usuario dado de baja');

-- Insertar Usuarios (10 records)
INSERT INTO Usuarios (usuario, clave) VALUES
('usuario1', 'clave123'),
('usuario2', 'clave456'),
('usuario3', 'clave789'),
('usuario4', 'clave012'),
('usuario5', 'clave345'),
('usuario6', 'clave678'),
('usuario7', 'clave901'),
('usuario8', 'clave234'),
('usuario9', 'clave567'),
('usuario10', 'clave890');

-- Insertar Miembros (10 records)
INSERT INTO Miembros (RUT, id_usuario, nombre, apellido_paterno, apellido_materno, fecha_inscripcion, id_estado) VALUES
('12345678-9', 1, 'Juan', 'P�rez', 'Gonz�lez', '2023-01-01', 1),
('23456789-0', 2, 'Mar�a', 'L�pez', 'Rodr�guez', '2023-01-02', 1),
('34567890-1', 3, 'Pedro', 'Garc�a', 'Mart�nez', '2023-01-03', 1),
('45678901-2', 4, 'Ana', 'Mart�nez', 'S�nchez', '2023-01-04', 1),
('56789012-3', 5, 'Luis', 'Rodr�guez', 'L�pez', '2023-01-05', 1),
('67890123-4', 6, 'Carmen', 'S�nchez', 'Garc�a', '2023-01-06', 2),
('78901234-5', 7, 'Jos�', 'Gonz�lez', 'P�rez', '2023-01-07', 1),
('89012345-6', 8, 'Laura', 'Torres', 'Flores', '2023-01-08', 1),
('90123456-7', 9, 'Diego', 'Flores', 'Torres', '2023-01-09', 1),
('01234567-8', 10, 'Sofia', 'D�az', 'Ruiz', '2023-01-10', 1);

-- Insertar Inventario (5 records)
INSERT INTO Inventario (id_libro, cantidad_disponible) VALUES
(1, 5),
(2, 3),
(3, 4),
(4, 2),
(5, 6);

-- Insertar Prestamos (10 records)
INSERT INTO Prestamos (id_libro, id_miembro, fecha_desde, fecha_hasta, fecha_devolucion) VALUES
(1, 1, '2024-01-01', '2024-01-15', '2024-01-14'),
(2, 2, '2024-01-02', '2024-01-16', '2024-01-15'),
(3, 3, '2024-01-03', '2024-01-17', '2024-01-16'),
(4, 4, '2024-01-04', '2024-01-18', '2024-01-17'),
(1, 5, '2024-01-05', '2024-01-19', '2024-01-18'),
(2, 6, '2024-01-06', '2024-01-20', '2024-01-19'),
(3, 7, '2024-01-07', '2024-01-21', '2024-01-20'),
(4, 8, '2024-01-08', '2024-01-22', '2024-01-21'),
(5, 9, '2024-01-09', '2024-01-23', '2024-01-22'),
(5, 10, '2024-01-10', '2024-01-24', '2024-01-23');

-- Insertar Roles (5 records)
INSERT INTO Roles (nombre, descripcion) VALUES
('Administrador', 'Control total del sistema'),
('Bibliotecario', 'Gesti�n de pr�stamos y devoluciones'),
('Usuario', 'Acceso b�sico al sistema'),
('Supervisor', 'Supervisi�n de operaciones'),
('Invitado', 'Acceso limitado solo lectura');

-- Insertar Permisos (5 records)
INSERT INTO Permisos (nombre, descripcion) VALUES
('Crear Usuario', 'Permite crear nuevos usuarios'),
('Modificar Usuario', 'Permite modificar usuarios existentes'),
('Eliminar Usuario', 'Permite eliminar usuarios'),
('Gestionar Pr�stamos', 'Permite gestionar pr�stamos'),
('Consultar Cat�logo', 'Permite consultar el cat�logo');

-- Insertar Roles_Usuarios (5 records)
INSERT INTO Roles_Usuarios (id_rol, id_usuario) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insertar Roles_Permisos (5 records)
INSERT INTO Roles_Permisos (id_rol, id_permiso) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5);