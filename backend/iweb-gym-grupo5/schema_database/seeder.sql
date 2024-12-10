-- Datos ficticios para la tabla 'actividades'
INSERT INTO public.actividades (dia_semana, fecha_fin, fecha_inicio, hora_fin, hora_inicio, nombre, tipo_actividad_id) VALUES
('Lunes', '2024-12-15 10:00:00', '2024-12-15 09:00:00', '10:00', '09:00', 'Yoga Matutino', 1),
('Martes', '2024-12-16 18:00:00', '2024-12-16 17:00:00', '18:00', '17:00', 'Zumba', 2),
('Miércoles', '2024-12-17 20:00:00', '2024-12-17 19:00:00', '20:00', '19:00', 'Pilates', 1),
('Jueves', '2024-12-18 22:00:00', '2024-12-18 21:00:00', '22:00', '21:00', 'Cardio', 3),
('Viernes', '2024-12-19 14:00:00', '2024-12-19 13:00:00', '14:00', '13:00', 'Spinning', 2);

-- Datos ficticios para la tabla 'instalaciones'
INSERT INTO public.instalaciones (nombre) VALUES
('Gimnasio Principal'),
('Sala de Yoga'),
('Pista de Atletismo'),
('Piscina Climatizada'),
('Sala de Spinning');

-- Datos ficticios para la tabla 'monitores'
INSERT INTO public.monitores (activo, direccion, email, fecha_nacimiento, name, password, telefono) VALUES
(true, 'Calle Ficticia 123', 'monitor1@example.com', '1980-05-10', 'Juan Pérez', 'password1', '123456789'),
(true, 'Avenida Imaginaria 456', 'monitor2@example.com', '1985-07-20', 'Ana García', 'password2', '987654321'),
(false, 'Calle Real 789', 'monitor3@example.com', '1990-02-15', 'Carlos Sánchez', 'password3', '555123456'),
(true, 'Calle Nueva 321', 'monitor4@example.com', '1975-11-30', 'Laura Martínez', 'password4', '666987654'),
(true, 'Avenida Sol 654', 'monitor5@example.com', '1992-04-25', 'Marta Ruiz', 'password5', '777321987');

-- Datos ficticios para la tabla 'reservas'
INSERT INTO public.reservas (fecha, actividad_id, socio_id) VALUES
('2024-12-15 09:30:00', 1, 1),
('2024-12-16 17:30:00', 2, 2),
('2024-12-17 19:30:00', 3, 3),
('2024-12-18 21:30:00', 4, 4),
('2024-12-19 13:30:00', 5, 5);

-- Datos ficticios para la tabla 'reservas_instalacion'
INSERT INTO public.reservas_instalacion (fecha, instalacion_id, socio_id) VALUES
('2024-12-15', 1, 1),
('2024-12-16', 2, 2),
('2024-12-17', 3, 3),
('2024-12-18', 4, 4),
('2024-12-19', 5, 5);

-- Datos ficticios para la tabla 'socios'
INSERT INTO public.socios (activo, direccion, email, fecha_nacimiento, name, password, telefono, saldo, fecha_alta, fecha_baja, tipo_cuota, inscripcion_id) VALUES
(true, 'Calle Ficticia 123', 'socio1@example.com', '1995-05-10', 'Pedro López', 'password1', '123456789', 100.0, '2024-01-10', null, 'Mensual', 1),
(true, 'Avenida Imaginaria 456', 'socio2@example.com', '1988-08-20', 'María Fernández', 'password2', '987654321', 150.0, '2024-02-15', null, 'Anual', 2),
(true, 'Calle Real 789', 'socio3@example.com', '1992-12-25', 'José Rodríguez', 'password3', '555123456', 80.0, '2024-03-05', null, 'Mensual', 3),
(false, 'Calle Nueva 321', 'socio4@example.com', '1990-07-15', 'Luis García', 'password4', '666987654', 200.0, '2024-04-10', '2024-10-10', 'Anual', 4),
(true, 'Avenida Sol 654', 'socio5@example.com', '1989-01-30', 'Carmen Ruiz', 'password5', '777321987', 50.0, '2024-05-25', null, 'Mensual', 5);

-- Datos ficticios para la tabla 'suscripcion'
INSERT INTO public.suscripcion (price, tipo_suscripcion) VALUES
(30.0, 'Mensual'),
(300.0, 'Anual'),
(150.0, 'Mensual'),
(200.0, 'Anual'),
(120.0, 'Mensual');

-- Datos ficticios para la tabla 'tipos_actividad'
INSERT INTO public.tipos_actividad (nombre, precio_extra) VALUES
(0, 0.0),
(1, 10.0),
(2, 20.0),
(3, 30.0);

-- Datos ficticios para la tabla 'web_masters'
INSERT INTO public.web_masters (activo, direccion, email, fecha_nacimiento, name, password, telefono) VALUES
(true, 'Calle Admin 1', 'admin1@example.com', '1980-04-10', 'Admin Juan', 'adminpass1', '123123123'),
(true, 'Avenida Admin 2', 'admin2@example.com', '1985-06-25', 'Admin Ana', 'adminpass2', '321321321');

-- Datos ficticios para la tabla 'webmaster_actividad'
INSERT INTO public.webmaster_actividad (webmaster_id, actividad_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4);

