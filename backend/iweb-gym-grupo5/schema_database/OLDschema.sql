--
-- PostgreSQL database dump
--

-- Dumped from database version 13.16 (Debian 13.16-1.pgdg120+1)
-- Dumped by pg_dump version 13.16 (Debian 13.16-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: actividades; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.actividades (
    id bigint NOT NULL,
    dia_semana character varying(255),
    fecha_fin timestamp(6) without time zone,
    fecha_inicio timestamp(6) without time zone,
    hora_fin character varying(255),
    hora_inicio character varying(255),
    nombre character varying(255),
    tipo_actividad_id bigint
);


ALTER TABLE public.actividades OWNER TO iw;

--
-- Name: actividades_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.actividades ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.actividades_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: instalaciones; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.instalaciones (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.instalaciones OWNER TO iw;

--
-- Name: instalaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.instalaciones ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.instalaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: monitores; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.monitores (
    id bigint NOT NULL,
    activo boolean,
    direccion character varying(255),
    email character varying(255),
    fecha_nacimiento character varying(255),
    name character varying(255),
    password character varying(255),
    telefono character varying(255)
);


ALTER TABLE public.monitores OWNER TO iw;

--
-- Name: reservas; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.reservas (
    id bigint NOT NULL,
    fecha timestamp(6) without time zone,
    actividad_id bigint,
    socio_id bigint
);


ALTER TABLE public.reservas OWNER TO iw;

--
-- Name: reservas_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.reservas ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.reservas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: reservas_instalacion; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.reservas_instalacion (
    id bigint NOT NULL,
    fecha date,
    instalacion_id bigint NOT NULL,
    socio_id bigint NOT NULL
);


ALTER TABLE public.reservas_instalacion OWNER TO iw;

--
-- Name: reservas_instalacion_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.reservas_instalacion ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.reservas_instalacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: socios; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.socios (
    id bigint NOT NULL,
    activo boolean,
    direccion character varying(255),
    email character varying(255),
    fecha_nacimiento character varying(255),
    name character varying(255),
    password character varying(255),
    telefono character varying(255),
    saldo real NOT NULL,
    fecha_alta character varying(255),
    fecha_baja character varying(255),
    tipo_cuota character varying(255),
    inscripcion_id bigint
);


ALTER TABLE public.socios OWNER TO iw;

--
-- Name: suscripcion; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.suscripcion (
    id bigint NOT NULL,
    price real NOT NULL,
    tipo_suscripcion character varying(255)
);


ALTER TABLE public.suscripcion OWNER TO iw;

--
-- Name: suscripcion_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.suscripcion ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.suscripcion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: tipos_actividad; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.tipos_actividad (
    id bigint NOT NULL,
    nombre smallint,
    precio_extra double precision NOT NULL,
    CONSTRAINT tipos_actividad_nombre_check CHECK (((nombre >= 0) AND (nombre <= 3)))
);


ALTER TABLE public.tipos_actividad OWNER TO iw;

--
-- Name: tipos_actividad_id_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

ALTER TABLE public.tipos_actividad ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tipos_actividad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: iw
--

CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_seq OWNER TO iw;

--
-- Name: web_masters; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.web_masters (
    id bigint NOT NULL,
    activo boolean,
    direccion character varying(255),
    email character varying(255),
    fecha_nacimiento character varying(255),
    name character varying(255),
    password character varying(255),
    telefono character varying(255)
);


ALTER TABLE public.web_masters OWNER TO iw;

--
-- Name: webmaster_actividad; Type: TABLE; Schema: public; Owner: iw
--

CREATE TABLE public.webmaster_actividad (
    webmaster_id bigint NOT NULL,
    actividad_id bigint NOT NULL
);


ALTER TABLE public.webmaster_actividad OWNER TO iw;

--
-- Name: actividades actividades_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.actividades
    ADD CONSTRAINT actividades_pkey PRIMARY KEY (id);


--
-- Name: instalaciones instalaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.instalaciones
    ADD CONSTRAINT instalaciones_pkey PRIMARY KEY (id);


--
-- Name: monitores monitores_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.monitores
    ADD CONSTRAINT monitores_pkey PRIMARY KEY (id);


--
-- Name: reservas_instalacion reservas_instalacion_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas_instalacion
    ADD CONSTRAINT reservas_instalacion_pkey PRIMARY KEY (id);


--
-- Name: reservas reservas_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT reservas_pkey PRIMARY KEY (id);


--
-- Name: socios socios_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.socios
    ADD CONSTRAINT socios_pkey PRIMARY KEY (id);


--
-- Name: suscripcion suscripcion_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.suscripcion
    ADD CONSTRAINT suscripcion_pkey PRIMARY KEY (id);


--
-- Name: tipos_actividad tipos_actividad_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.tipos_actividad
    ADD CONSTRAINT tipos_actividad_pkey PRIMARY KEY (id);


--
-- Name: web_masters web_masters_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.web_masters
    ADD CONSTRAINT web_masters_pkey PRIMARY KEY (id);


--
-- Name: webmaster_actividad webmaster_actividad_pkey; Type: CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.webmaster_actividad
    ADD CONSTRAINT webmaster_actividad_pkey PRIMARY KEY (webmaster_id, actividad_id);


--
-- Name: webmaster_actividad fk5tc4vmc34gdf0m7q9ft6bkvwy; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.webmaster_actividad
    ADD CONSTRAINT fk5tc4vmc34gdf0m7q9ft6bkvwy FOREIGN KEY (actividad_id) REFERENCES public.actividades(id);


--
-- Name: reservas fk7alcxmef09kvhmvxwk21ieuwk; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT fk7alcxmef09kvhmvxwk21ieuwk FOREIGN KEY (actividad_id) REFERENCES public.actividades(id);


--
-- Name: actividades fkb5c8ktxv3rhda0p6uixkallka; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.actividades
    ADD CONSTRAINT fkb5c8ktxv3rhda0p6uixkallka FOREIGN KEY (tipo_actividad_id) REFERENCES public.tipos_actividad(id);


--
-- Name: reservas fkfh5n1bwcolcv2vi7sa721x7sd; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT fkfh5n1bwcolcv2vi7sa721x7sd FOREIGN KEY (socio_id) REFERENCES public.socios(id);


--
-- Name: socios fkkyxrg7wljoehac6hn72ba7uh; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.socios
    ADD CONSTRAINT fkkyxrg7wljoehac6hn72ba7uh FOREIGN KEY (inscripcion_id) REFERENCES public.suscripcion(id);


--
-- Name: webmaster_actividad fkmbu80hwaxtd04vogobtcy0ocv; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.webmaster_actividad
    ADD CONSTRAINT fkmbu80hwaxtd04vogobtcy0ocv FOREIGN KEY (webmaster_id) REFERENCES public.web_masters(id);


--
-- Name: reservas_instalacion fkn19ur3qc9j6nh7o9lnp5u5wbf; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas_instalacion
    ADD CONSTRAINT fkn19ur3qc9j6nh7o9lnp5u5wbf FOREIGN KEY (socio_id) REFERENCES public.socios(id);


--
-- Name: reservas_instalacion fkp4ix7bjfsmofxyac06wyftmg9; Type: FK CONSTRAINT; Schema: public; Owner: iw
--

ALTER TABLE ONLY public.reservas_instalacion
    ADD CONSTRAINT fkp4ix7bjfsmofxyac06wyftmg9 FOREIGN KEY (instalacion_id) REFERENCES public.instalaciones(id);


--
-- PostgreSQL database dump complete
--

