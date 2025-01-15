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
