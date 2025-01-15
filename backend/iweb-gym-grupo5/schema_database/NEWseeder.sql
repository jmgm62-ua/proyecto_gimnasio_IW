-- Drop existing sequences if they exist
DROP SEQUENCE IF EXISTS public.actividad_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.instalacion_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.reserva_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.reserva_instalacion_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.tipo_actividad_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.suscripcion_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.webmaster_actividad_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.ingreso_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.ingreso_pendiente_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.moroso_id_seq CASCADE;

-- Create sequences
CREATE SEQUENCE public.actividad_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.instalacion_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.reserva_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.reserva_instalacion_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.tipo_actividad_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.suscripcion_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.webmaster_actividad_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.ingreso_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.ingreso_pendiente_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE public.moroso_id_seq START WITH 1 INCREMENT BY 1;

-- Alter tables to use sequences
ALTER TABLE public.actividades ALTER COLUMN id SET DEFAULT nextval('public.actividad_id_seq');
ALTER TABLE public.instalaciones ALTER COLUMN id SET DEFAULT nextval('public.instalacion_id_seq');
ALTER TABLE public.reservas ALTER COLUMN id SET DEFAULT nextval('public.reserva_id_seq');
ALTER TABLE public.reservas_instalacion ALTER COLUMN id SET DEFAULT nextval('public.reserva_instalacion_id_seq');
ALTER TABLE public.tipos_actividad ALTER COLUMN id SET DEFAULT nextval('public.tipo_actividad_id_seq');
ALTER TABLE public.suscripcion ALTER COLUMN id SET DEFAULT nextval('public.suscripcion_id_seq');
ALTER TABLE public.webmaster_actividad ALTER COLUMN id SET DEFAULT nextval('public.webmaster_actividad_id_seq');
ALTER TABLE public.ingresos ALTER COLUMN id SET DEFAULT nextval('public.ingreso_id_seq');
ALTER TABLE public.ingresos_pendientes ALTER COLUMN id SET DEFAULT nextval('public.ingreso_pendiente_id_seq');
ALTER TABLE public.morosos ALTER COLUMN id SET DEFAULT nextval('public.moroso_id_seq');

-- Set sequence values to start after existing data
SELECT setval('public.actividad_id_seq', (SELECT MAX(id) FROM public.actividades));
SELECT setval('public.instalacion_id_seq', (SELECT MAX(id) FROM public.instalaciones));
SELECT setval('public.reserva_id_seq', (SELECT MAX(id) FROM public.reservas));
SELECT setval('public.reserva_instalacion_id_seq', (SELECT MAX(id) FROM public.reservas_instalacion));
SELECT setval('public.tipo_actividad_id_seq', (SELECT MAX(id) FROM public.tipos_actividad));
SELECT setval('public.suscripcion_id_seq', (SELECT MAX(id) FROM public.suscripcion));
SELECT setval('public.ingreso_id_seq', (SELECT MAX(id) FROM public.ingresos));
SELECT setval('public.ingreso_pendiente_id_seq', (SELECT MAX(id) FROM public.ingresos_pendientes));
SELECT setval('public.moroso_id_seq', (SELECT MAX(id) FROM public.morosos));


-- Clear existing data
DELETE FROM public.webmaster_actividad;
DELETE FROM public.reservas_instalacion;
DELETE FROM public.reservas;
DELETE FROM public.actividades;
DELETE FROM public.socios;
DELETE FROM public.monitores;
DELETE FROM public.instalaciones;
DELETE FROM public.tipos_actividad;
DELETE FROM public.suscripcion;
DELETE FROM public.web_masters;
DELETE FROM public.ingresos;
DELETE FROM public.ingresos_pendientes;
DELETE FROM public.morosos;

-- Reset sequences
ALTER SEQUENCE public.user_id_seq RESTART WITH 1;

-- Eliminar la secuencia si ya existe (usando CASCADE para eliminar dependencias)
DROP SEQUENCE IF EXISTS public.user_id_seq CASCADE;

-- Crear la secuencia para generar IDs únicos
CREATE SEQUENCE public.user_id_seq START WITH 1 INCREMENT BY 1;

-- Configurar la tabla socios para usar la secuencia
ALTER TABLE public.socios ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq');

-- Configurar la tabla monitores para usar la secuencia
ALTER TABLE public.monitores ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq');

-- Configurar la tabla web_masters para usar la secuencia
ALTER TABLE public.web_masters ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq');

-- Suscripcion (Mantenido igual)
INSERT INTO public.suscripcion (id, price, tipo_suscripcion) VALUES
(1, 30.0, 'Individual'),
(2, 60.0, 'Familiar');

-- Tipos Actividad (Mantenido igual)
INSERT INTO public.tipos_actividad (id, nombre, precio_extra) VALUES
(1, 0, 5.0),  -- Spinning
(2, 1, 15.0), -- Crossfit
(3, 2, 10.0), -- Pilates
(4, 3, 10.0); -- HIIT

-- Instalaciones (Mantenido igual)
INSERT INTO public.instalaciones (id, nombre) VALUES
(1, 'Sala principal'),
(2, 'Sala de ciclo'),
(3, 'Sala general 1'),
(4, 'Sala general 2'),
(5, 'Vestuarios'),
(6, 'Recepción'),
(7, 'Cafetería'),
(8, 'Sala para estiramientos');

-- Socios
INSERT INTO public.socios (id, activo, direccion, email, fecha_nacimiento, name, password, telefono, saldo, fecha_alta, fecha_baja, tipo_cuota, inscripcion_id) VALUES
(nextval('user_id_seq'), true, 'Calle Ficticia 123', 'socio1@example.com', '1995-05-10', 'Pedro López', 'password1', '123456789', 100.0, '2025-01-10', null, 'Individual', 1),
(nextval('user_id_seq'), true, 'Avenida Imaginaria 456', 'socio2@example.com', '1988-08-20', 'María Fernández', 'password2', '987654321', 150.0, '2025-02-15', null, 'Familiar', 2),
(nextval('user_id_seq'), true, 'Calle Real 789', 'socio3@example.com', '1992-12-25', 'José Rodríguez', 'password3', '555123456', 80.0, '2025-03-05', null, 'Individual', 1),
(nextval('user_id_seq'), true, 'Calle Nueva 321', 'socio4@example.com', '1990-07-15', 'Luis García', 'password4', '666987654', 200.0, '2025-04-10', '2025-10-10', 'Familiar', 2),
(nextval('user_id_seq'), true, 'Avenida Sol 654', 'socio5@example.com', '1989-01-30', 'Carmen Ruiz', 'password5', '777321987', 50.0, '2025-05-25', '2025-12-17', 'Individual', 1),
(nextval('user_id_seq'), true, 'Calle Laurel 789', 'socio6@example.com', '1993-09-15', 'Elena Martínez', 'password6', '111222333', 125.0, '2025-06-01', null, 'Familiar', 2),
(nextval('user_id_seq'), true, 'Avenida Pinos 234', 'socio7@example.com', '1987-03-28', 'Roberto Sánchez', 'password7', '444555666', 175.0, '2025-06-15', null, 'Individual', 1),
(nextval('user_id_seq'), false, 'Plaza Mayor 567', 'socio8@example.com', '1991-11-03', 'Laura Torres', 'password8', '777888999', 90.0, '2025-07-01', '2025-12-31', 'Familiar', 2),
(nextval('user_id_seq'), true, 'Calle Robles 890', 'socio9@example.com', '1994-06-22', 'Carlos Navarro', 'password9', '123987456', 220.0, '2025-07-15', null, 'Individual', 1),
(nextval('user_id_seq'), true, 'Avenida Central 432', 'socio10@example.com', '1986-12-08', 'Isabel Moreno', 'password10', '456789123', 75.0, '2025-08-01', null, 'Familiar', 2),
-- Socios sin fecha de alta
(nextval('user_id_seq'), false, 'Calle Ficticia 123', 'socio11@example.com', null, 'Juan Perez', 'password11', '123456789', 100.0, null, null, 'Familiar', 2),
(nextval('user_id_seq'), false, 'Avenida Imaginaria 456', 'socio12@example.com', null, 'María Fernández', 'password12', '987654321', 150.0, null, null, 'Familiar', 2),
(nextval('user_id_seq'), false, 'Calle Real 789', 'socio13@example.com', null, 'José Rodríguez', 'password13', '555123456', 80.0, null, null, 'Individual', 1),
(nextval('user_id_seq'), false, 'Calle Nueva 321', 'socio14@example.com', null, 'Luis García', 'password14', '666987654', 200.0, null, null, 'Familiar', 2);

-- Monitores (Mantenido igual)
INSERT INTO public.monitores (id, activo, direccion, email, fecha_nacimiento, name, password, telefono) VALUES
(nextval('user_id_seq'), true, 'Calle Ficticia 123', 'monitor1@example.com', '1980-05-10', 'Juan Pérez', 'password1', '123456789'),
(nextval('user_id_seq'), true, 'Avenida Imaginaria 456', 'monitor2@example.com', '1985-07-20', 'Ana García', 'password2', '987654321'),
(nextval('user_id_seq'), false, 'Calle Real 789', 'monitor3@example.com', '1990-02-15', 'Carlos Sánchez', 'password3', '555123456'),
(nextval('user_id_seq'), true, 'Calle Nueva 321', 'monitor4@example.com', '1975-11-30', 'Laura Martínez', 'password4', '666987654'),
(nextval('user_id_seq'), true, 'Avenida Sol 654', 'monitor5@example.com', '1992-04-25', 'Marta Ruiz', 'password5', '777321987');

-- Web Masters (Mantenido igual)
INSERT INTO public.web_masters (id, activo, direccion, email, fecha_nacimiento, name, password, telefono)
VALUES
(nextval('user_id_seq'), true, 'Calle Admin 1', 'admin1@example.com', '1980-04-10', 'Admin Juan', 'adminpass1', '123123123'),
(nextval('user_id_seq'), true, 'Avenida Admin 2', 'admin2@example.com', '1985-06-25', 'Admin Ana', 'adminpass2', '321321321');

-- Actividades
INSERT INTO public.actividades (id, dia_semana, fecha_fin, fecha_inicio, hora_fin, hora_inicio, nombre, tipo_actividad_id, monitor_id) VALUES
(1, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '19:00', 'Spinning', 1, 15),
(2, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '19:00', 'Spinning', 1, 15),
(3, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '19:00', 'Spinning', 1, 16),
(4, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '19:00', 'Spinning', 1, 16),
(5, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '19:00', 'Spinning', 1, 15),
(6, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Spinning', 1, 15),
(7, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 15),
(8, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 17),
(9, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 15),
(10, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 16),
(11, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 17),
(12, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 17),
(13, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 15),
(14, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 15),
(15, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 16),
(16, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 17),
(17, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 16),
(18, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 16),
(19, 'Domingo', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '11:00', '10:00', 'Pilates', 3, 15),
(20, 'Domingo', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '18:00', '17:00', 'Pilates', 3, 16),
(21, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 15),
(22, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 15),
(23, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 15),
(24, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 16),
(25, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 17),
(26, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '20:00', '18:00', 'HIIT', 4, 18),
(27, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 19),
(28, 'Lunes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 17),
(29, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 16),
(30, 'Martes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 17),
(31, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 17),
(32, 'Miércoles', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 16),
(33, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 15),
(34, 'Jueves', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 15),
(35, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 15),
(36, 'Viernes', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 16),
(37, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 16),
(38, 'Sábado', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 16),
(39, 'Domingo', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '10:30', '09:00', 'Crossfit', 2, 15),
(40, 'Domingo', '2026-12-31 23:59:00', '2025-01-01 00:00:00', '21:00', '20:00', 'Crossfit', 2, 15);

-- Reservas
INSERT INTO public.reservas (id, fecha, actividad_id, socio_id) VALUES
-- Spinning reservations
(1, '2025-01-03 19:00:00', 2, 1),    -- Tuesday Spinning for Pedro
(2, '2025-01-04 10:00:00', 3, 3),    -- Wednesday Spinning for José
(3, '2025-01-04 10:00:00', 6, 7),    -- Saturday Spinning for Roberto
(31, '2025-01-04 10:00:00', 6, 8),   -- Saturday Spinning for Isabel
(32, '2025-01-04 10:00:00', 6, 9),   -- Saturday Spinning for Elena

-- Pilates reservations
(4, '2025-01-03 10:00:00', 16, 2),    -- Tuesday morning Pilates for María
(5, '2025-01-03 10:00:00', 16, 6),   -- Tuesday evening Pilates for Elena
(6, '2025-01-03 17:00:00', 16, 9),   -- Wednesday morning Pilates for Carlos
(7, '2025-01-03 17:00:00', 12, 10),  -- Wednesday evening Pilates for Isabel
(8, '2025-01-03 17:00:00', 13, 2),   -- Thursday morning Pilates for María
(9, '2025-01-04 17:00:00', 14, 3),   -- Thursday evening Pilates for José
(10, '2025-01-04 17:00:00', 19, 7),  -- Sunday morning Pilates for Roberto

-- HIIT reservations
(11, '2025-01-17 17:45:00', 22, 1),  -- Tuesday HIIT for Pedro
(12, '2025-01-18 17:50:00', 23, 6),  -- Wednesday HIIT for Elena
(13, '2025-01-19 17:45:00', 24, 9),  -- Thursday HIIT for Carlos
(14, '2025-01-20 17:40:00', 25, 10), -- Friday HIIT for Isabel
(15, '2025-01-21 17:45:00', 26, 2),  -- Saturday HIIT for María

-- Crossfit reservations
(16, '2025-01-04 20:00:00', 29, 3),  -- Tuesday morning Crossfit for José
(17, '2025-01-04 20:00:00', 30, 7),  -- Tuesday evening Crossfit for Roberto
(18, '2025-01-04 20:00:00', 31, 1),  -- Wednesday morning Crossfit for Pedro
(19, '2025-01-04 20:00:00', 32, 2),  -- Wednesday evening Crossfit for María
(20, '2025-01-19 09:15:00', 33, 6),  -- Thursday morning Crossfit for Elena
(21, '2025-01-19 19:45:00', 34, 9),  -- Thursday evening Crossfit for Carlos
(22, '2025-01-20 09:15:00', 35, 10), -- Friday morning Crossfit for Isabel
(23, '2025-01-20 19:45:00', 36, 3),  -- Friday evening Crossfit for José
(24, '2025-01-21 09:15:00', 37, 7),  -- Saturday morning Crossfit for Roberto
(25, '2025-01-22 09:15:00', 39, 1),  -- Sunday morning Crossfit for Pedro

-- Additional mixed reservations
(26, '2025-01-23 18:45:00', 1, 2),   -- Monday Spinning for María
(27, '2025-01-23 17:45:00', 8, 6),   -- Monday evening Pilates for Elena
(28, '2025-01-23 17:45:00', 21, 9),  -- Monday HIIT for Carlos
(29, '2025-01-23 09:15:00', 27, 10), -- Monday morning Crossfit for Isabel
(30, '2025-01-23 19:45:00', 28, 7);

-- Reservas Instalacion (Actualizadas las fechas para 2025)
INSERT INTO public.reservas_instalacion (id, fecha, instalacion_id, socio_id) VALUES
(1, '2025-01-17 08:00:00', 1, 1),
(2, '2025-01-17 10:00:00', 1, 2),    -- María, media mañana
(3, '2025-01-17 16:00:00', 1, 3),    -- José, tarde
(4, '2025-01-18 09:00:00', 1, 6),    -- Elena, mañana
(5, '2025-01-18 17:00:00', 1, 7),    -- Roberto, tarde

-- Sala de ciclo (2)
(6, '2025-01-17 07:30:00', 2, 9),    -- Carlos, primera hora
(7, '2025-01-17 18:30:00', 2, 10),   -- Isabel, tarde
(8, '2025-01-18 08:30:00', 2, 1),    -- Pedro, mañana
(9, '2025-01-18 19:30:00', 2, 2),    -- María, noche
(10, '2025-01-19 17:30:00', 2, 3),   -- José, tarde

-- Sala general 1 (3)
(11, '2025-01-17 11:00:00', 3, 6),   -- Elena, media mañana
(12, '2025-01-17 15:00:00', 3, 7),   -- Roberto, tarde
(13, '2025-01-18 10:00:00', 3, 9),   -- Carlos, mañana
(14, '2025-01-18 16:00:00', 3, 10),  -- Isabel, tarde
(15, '2025-01-19 12:00:00', 3, 1),   -- Pedro, mediodía

-- Sala general 2 (4)
(16, '2025-01-17 09:30:00', 4, 2),   -- María, mañana
(17, '2025-01-17 14:30:00', 4, 3),   -- José, tarde
(18, '2025-01-18 11:30:00', 4, 6),   -- Elena, mediodía
(19, '2025-01-18 18:30:00', 4, 7),   -- Roberto, tarde
(20, '2025-01-19 10:30:00', 4, 9),   -- Carlos, mañana

-- Sala para estiramientos (8)
(21, '2025-01-17 13:00:00', 8, 10),  -- Isabel, mediodía
(22, '2025-01-17 19:00:00', 8, 1),   -- Pedro, noche
(23, '2025-01-18 12:00:00', 8, 2),   -- María, mediodía
(24, '2025-01-18 20:00:00', 8, 3),   -- José, noche
(25, '2025-01-19 11:00:00', 8, 6),   -- Elena, mañana

-- Reservas adicionales variadas
(26, '2025-01-19 15:30:00', 1, 7),   -- Roberto, sala principal
(27, '2025-01-19 16:30:00', 2, 9),   -- Carlos, sala de ciclo
(28, '2025-01-19 17:30:00', 3, 10),  -- Isabel, sala general 1
(29, '2025-01-19 18:30:00', 4, 1),   -- Pedro, sala general 2
(30, '2025-01-19 19:30:00', 8, 2);

-- Webmaster Actividad (Mantenido igual)
INSERT INTO public.webmaster_actividad (webmaster_id, actividad_id) VALUES
(20, 1),
(20, 2),
(21, 3),
(21, 4);

-- NUEVAS TABLAS --

-- Ingresos
INSERT INTO public.ingresos (id, cantidad, fecha, referencia) VALUES
(1, 30.00, '2025-01-01 10:00:00', 'CUOTA-ENE-2025-SOCIO1'),
(2, 60.00, '2025-01-01 10:15:00', 'CUOTA-ENE-2025-SOCIO2'),
(3, 30.00, '2025-01-01 11:00:00', 'CUOTA-ENE-2025-SOCIO3'),
(4, 15.00, '2025-01-02 09:30:00', 'EXTRA-CROSSFIT-SOCIO1'),
(5, 10.00, '2025-01-02 10:45:00', 'EXTRA-PILATES-SOCIO2'),
(6, 5.00, '2025-01-03 15:20:00', 'EXTRA-SPINNING-SOCIO3'),
(7, 60.00, '2025-01-05 16:00:00', 'CUOTA-ENE-2025-SOCIO6'),
(8, 30.00, '2025-01-05 17:30:00', 'CUOTA-ENE-2025-SOCIO7'),
(9, 15.00, '2025-01-06 11:15:00', 'EXTRA-CROSSFIT-SOCIO6'),
(10, 10.00, '2025-01-07 12:00:00', 'EXTRA-HIIT-SOCIO7'),
(11, 30.00, '2025-02-01 10:00:00', 'CUOTA-FEB-2025-SOCIO1'),
(12, 60.00, '2025-02-01 10:15:00', 'CUOTA-FEB-2025-SOCIO2'),
(13, 30.00, '2025-02-01 11:00:00', 'CUOTA-FEB-2025-SOCIO3'),
(14, 60.00, '2025-02-05 16:00:00', 'CUOTA-FEB-2025-SOCIO6'),
(15, 30.00, '2025-02-05 17:30:00', 'CUOTA-FEB-2025-SOCIO7');

-- Ingresos Pendientes
INSERT INTO public.ingresos_pendientes (id, cobrado, referencia) VALUES
(1, false, 'CUOTA-ENE-2025-SOCIO4'),
(2, false, 'CUOTA-ENE-2025-SOCIO5'),
(3, false, 'CUOTA-ENE-2025-SOCIO8'),
(4, true, 'EXTRA-CROSSFIT-SOCIO9'),
(5, true, 'EXTRA-HIIT-SOCIO10'),
(6, false, 'CUOTA-FEB-2025-SOCIO4'),
(7, false, 'CUOTA-FEB-2025-SOCIO5'),
(8, false, 'CUOTA-FEB-2025-SOCIO8'),
(9, false, 'EXTRA-PILATES-SOCIO9'),
(10, false, 'EXTRA-SPINNING-SOCIO10');

-- Morosos
INSERT INTO public.morosos (id, id_socio, mensualidad_no_pagada) VALUES
(1, 4, '01-2025'),
(2, 3, '01-2025'),
(3, 5, '01-2025');