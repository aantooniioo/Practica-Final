SET sql_safe_updates = 0;

DELETE FROM pr_empleados_proyecto;
DELETE FROM pr_proyectos;
DELETE FROM em_empleados;

ALTER TABLE em_empleados AUTO_INCREMENT = 1;
ALTER TABLE pr_proyectos AUTO_INCREMENT = 1;

INSERT INTO em_empleados 
(TX_NIF, TX_NOMBRE, TX_APELLIDO1, TX_APELLIDO2, F_NACIMIENTO, N_TELEFONO1, N_TELEFONO2, TX_EMAIL, F_ALTA, CX_EDOCIVIL, B_FORMACIONU)
VALUES
('03493911G', 'Antonio', 'Ángeles', 'Blázquez', '2005-09-08', '600000000', '600000001', 'aangeles@futurespace.es', '2026-03-02', 'S', 'N'),
('00000000A', 'Alejandro', 'Blázquez', 'Santos', '1978-05-23', '700000000', '700000001', 'absantos@futurespace.es', '2020-03-02', 'C', 'S'),
('34567890V', 'Juan', 'Martinez', 'Ruiz', '1998-03-12', '600111113', '600222224', 'jmruiz@futurespace.es', '2025-01-10', 'S', 'N'),
('45678901G', 'Laura', 'Fernandez', 'Diaz', '1995-07-21', '600111114', '600222225', 'lfdiaz@futurespace.es', '2025-02-15', 'C', 'S'),
('56789012H', 'Carlos', 'Gomez', 'Moreno', '1992-11-05', '600111115', '600222226', 'cgmoreno@futurespace.es', '2025-03-01', 'S', 'N'),
('67890123J', 'Lucia', 'Jimenez', 'Navarro', '1999-01-30', '600111116', '600222227', 'ljnavarro@futurespace.es', '2025-04-10', 'C', 'S'),
('78901234K', 'David', 'Torres', 'Vega', '1996-09-14', '600111117', '600222228', 'dtvega@futurespace.es', '2025-05-20', 'S', 'N'),
('89012345L', 'Elena', 'Ruiz', 'Santos', '1997-06-18', '600111118', '600222229', 'ersantos@futurespace.es', '2025-06-05', 'C', 'S'),
('90123456M', 'Pablo', 'Ortega', 'Molina', '1993-12-01', '600111119', '600222230', 'pomolina@futurespace.es', '2025-07-12', 'S', 'S'),
('11223344N', 'Sara', 'Delgado', 'Castro', '1994-08-23', '600111120', '600222231', 'sdcastro@futurespace.es', '2025-08-01', 'C', 'N'),
('22334455P', 'Miguel', 'Herrera', 'Flores', '1991-02-17', '600111121', '600222232', 'mhflores@futurespace.es', '2025-09-10', 'S', 'S'),
('33445566Q', 'Andrea', 'Medina', 'Cruz', '1998-05-09', '600111122', '600222233', 'amcruz@futurespace.es', '2025-10-15', 'C', 'N'),
('44556677R', 'Jorge', 'Castillo', 'Ramos', '1990-04-11', '600111123', '600222234', 'jcramos@futurespace.es', '2025-11-01', 'S', 'S'),
('55667788S', 'Patricia', 'Suarez', 'Ortega', '1996-10-28', '600111124', '600222235', 'psortega@futurespace.es', '2025-11-20', 'C', 'S'),
('66778899T', 'Alberto', 'Gil', 'Navarro', '1992-07-07', '600111125', '600222236', 'agnavarro@futurespace.es', '2025-12-05', 'S', 'N');

INSERT INTO pr_proyectos (TX_DESCRIPCION, F_INICIO, F_FIN, TX_LUGAR)
VALUES
('Sistema ERP interno', '2026-01-10', NULL, 'Madrid'),
('App movil clientes', '2026-02-15', NULL, 'Barcelona'),
('Migración a cloud', '2025-09-01', '2026-02-01', 'Valencia'),
('Portal web corporativo', '2026-03-01', NULL, 'Remoto'),
('Optimización base de datos', '2025-11-10', '2026-01-20', 'Sevilla'),
('Dashboard analítica', '2026-02-01', NULL, 'Bilbao'),
('Sistema de tickets', '2025-10-01', '2025-12-20', 'Madrid'),
('Integración API externa', '2026-03-15', NULL, 'Remoto'),
('Plataforma e-learning', '2025-08-01', '2025-12-10', 'Valencia'),
('Automatización procesos', '2026-01-25', NULL, 'Barcelona');

INSERT INTO pr_empleados_proyecto 
(ID_PROYECTO, ID_EMPLEADO, F_ALTA)
VALUES
(1, 1, '2026-01-10'),
(1, 2, '2026-01-10'),
(1, 3, '2026-01-12'),
(1, 4, '2026-01-15'),
(2, 2, '2026-02-15'),
(2, 5, '2026-02-16'),
(2, 6, '2026-02-18'),
(3, 7, '2025-09-05'),
(3, 8, '2025-09-10'),
(4, 1, '2026-03-01'),
(4, 9, '2026-03-02'),
(4, 10, '2026-03-05'),
(5, 3, '2025-11-15'),
(5, 4, '2025-11-18'),
(6, 11, '2026-02-05'),
(6, 12, '2026-02-07'),
(7, 6, '2025-10-05'),
(7, 7, '2025-10-08'),
(8, 8, '2026-03-16'),
(8, 9, '2026-03-18'),
(9, 10, '2025-08-10'),
(9, 11, '2025-08-12'),
(10, 12, '2026-01-26'),
(10, 13, '2026-01-28'),
(10, 14, '2026-01-28'),
(10, 15, '2026-01-28');
