-- Insertar datos ficticios en la tabla 'tipos_actividad'
INSERT INTO public.tipos_actividad (id, nombre, precio_extra)
SELECT
    nextval('tipos_actividad_id_seq'),
    (random() * 3)::smallint,
    round(random() * 100 + 10, 2)
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'actividades'
INSERT INTO public.actividades (id, dia_semana, fecha_fin, fecha_inicio, hora_fin, hora_inicio, nombre, tipo_actividad_id)
SELECT
    nextval('actividades_id_seq'),
    CASE WHEN random() > 0.5 THEN 'Lunes' ELSE 'Martes' END,
    now() + (random() * interval '30 days'),
    now() + (random() * interval '15 days'),
    to_char((now() + (random() * interval '10 hours'))::time, 'HH24:MI'),
    to_char((now() + (random() * interval '10 hours'))::time, 'HH24:MI'),
    'Actividad ' || generate_series,
    (random() * 100 + 1)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'instalaciones'
INSERT INTO public.instalaciones (id, nombre)
SELECT
    nextval('instalaciones_id_seq'),
    'Instalación ' || generate_series
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'monitores'
INSERT INTO public.monitores (id, activo, direccion, email, fecha_nacimiento, name, password, telefono)
SELECT
    nextval('monitores_id_seq'),
    random() > 0.5,
    'Calle Falsa ' || generate_series,
    'monitor' || generate_series || '@example.com',
    to_char((now() - (random() * interval '30 years'))::date, 'YYYY-MM-DD'),
    'Monitor ' || generate_series,
    md5(random()::text),
    '600' || (random() * 1000000)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'socios'
INSERT INTO public.socios (id, activo, direccion, email, fecha_nacimiento, name, password, telefono, saldo, fecha_alta, fecha_baja, tipo_cuota, inscripcion_id)
SELECT
    nextval('socios_id_seq'),
    random() > 0.5,
    'Avenida Principal ' || generate_series,
    'socio' || generate_series || '@example.com',
    to_char((now() - (random() * interval '60 years'))::date, 'YYYY-MM-DD'),
    'Socio ' || generate_series,
    md5(random()::text),
    '700' || (random() * 1000000)::bigint,
    round(random() * 500, 2),
    to_char((now() - (random() * interval '5 years'))::date, 'YYYY-MM-DD'),
    NULL,
    'Cuota ' || generate_series % 4,
    (random() * 100 + 1)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'reservas'
INSERT INTO public.reservas (id, fecha, actividad_id, socio_id)
SELECT
    nextval('reservas_id_seq'),
    now() + (random() * interval '30 days'),
    (random() * 100 + 1)::bigint,
    (random() * 100 + 1)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'reservas_instalacion'
INSERT INTO public.reservas_instalacion (id, fecha, instalacion_id, socio_id)
SELECT
    nextval('reservas_instalacion_id_seq'),
    now() + (random() * interval '30 days'),
    (random() * 100 + 1)::bigint,
    (random() * 100 + 1)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'suscripcion'
INSERT INTO public.suscripcion (id, price, tipo_suscripcion)
SELECT
    nextval('suscripcion_id_seq'),
    round(random() * 50 + 10, 2),
    CASE WHEN random() > 0.5 THEN 'Mensual' ELSE 'Anual' END
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'web_masters'
INSERT INTO public.web_masters (id, activo, direccion, email, fecha_nacimiento, name, password, telefono)
SELECT
    nextval('web_masters_id_seq'),
    random() > 0.5,
    'Calle Webmaster ' || generate_series,
    'webmaster' || generate_series || '@example.com',
    to_char((now() - (random() * interval '50 years'))::date, 'YYYY-MM-DD'),
    'Webmaster ' || generate_series,
    md5(random()::text),
    '800' || (random() * 1000000)::bigint
FROM generate_series(1, 100);

-- Insertar datos ficticios en la tabla 'webmaster_actividad'
INSERT INTO public.webmaster_actividad (webmaster_id, actividad_id)
SELECT
    (random() * 100 + 1)::bigint,
    (random() * 100 + 1)::bigint
FROM generate_series(1, 100);

