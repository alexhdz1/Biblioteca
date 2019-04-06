--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)

-- Started on 2019-03-17 17:38:29 CST

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13081)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3015 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 32992)
-- Name: administrador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administrador (
    idadministrador integer NOT NULL,
    nombre text NOT NULL,
    correo text NOT NULL,
    contrasena text NOT NULL,
    num_trabajador text NOT NULL
);


ALTER TABLE public.administrador OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 32990)
-- Name: administrador_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.administrador_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.administrador_id_seq OWNER TO postgres;

--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 196
-- Name: administrador_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.administrador_id_seq OWNED BY public.administrador.idadministrador;


--
-- TOC entry 201 (class 1259 OID 33015)
-- Name: edificio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.edificio (
    idedificio integer NOT NULL,
    nombreedificio text NOT NULL
);


ALTER TABLE public.edificio OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 33013)
-- Name: edificio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.edificio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.edificio_id_seq OWNER TO postgres;

--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 200
-- Name: edificio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.edificio_id_seq OWNED BY public.edificio.idedificio;


--
-- TOC entry 206 (class 1259 OID 33044)
-- Name: espaciocultural; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.espaciocultural (
    idevento integer NOT NULL,
    idsala integer NOT NULL,
    fecha date NOT NULL,
    nombreevento text NOT NULL,
    horaInicio time without time zone NOT NULL,
    horaFinal time without time zone NOT NULL,
    reservado boolean DEFAULT false NOT NULL 

);


ALTER TABLE public.espaciocultural OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 33040)
-- Name: espaciocultural_idevento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.espaciocultural_idevento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.espaciocultural_idevento_seq OWNER TO postgres;

--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 204
-- Name: espaciocultural_idevento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.espaciocultural_idevento_seq OWNED BY public.espaciocultural.idevento;


--
-- TOC entry 205 (class 1259 OID 33042)
-- Name: espaciocultural_idsala_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.espaciocultural_idsala_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.espaciocultural_idsala_seq OWNER TO postgres;

--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 205
-- Name: espaciocultural_idsala_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.espaciocultural_idsala_seq OWNED BY public.espaciocultural.idsala;


--
-- TOC entry 199 (class 1259 OID 33003)
-- Name: profesor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesor (
    idprofesor integer NOT NULL,
    nombre text NOT NULL,
    correo text NOT NULL,
    departamento text NOT NULL,
    tipoprof text NOT NULL,
    contrasena text NOT NULL,
    num_trabajador text NOT NULL,
    activo boolean DEFAULT false NOT NULL
);


ALTER TABLE public.profesor OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 33001)
-- Name: profesor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.profesor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.profesor_id_seq OWNER TO postgres;

--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 198
-- Name: profesor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profesor_id_seq OWNED BY public.profesor.idprofesor;


--
-- TOC entry 203 (class 1259 OID 33026)
-- Name: salacultural; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.salacultural (
    idsala integer NOT NULL,
    nombresala text NOT NULL,
    idedificio integer
);


ALTER TABLE public.salacultural OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 33024)
-- Name: salacultural_idsala_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.salacultural_idsala_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.salacultural_idsala_seq OWNER TO postgres;

--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 202
-- Name: salacultural_idsala_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.salacultural_idsala_seq OWNED BY public.salacultural.idsala;


--
-- TOC entry 2857 (class 2604 OID 32995)
-- Name: administrador idadministrador; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador ALTER COLUMN idadministrador SET DEFAULT nextval('public.administrador_id_seq'::regclass);


--
-- TOC entry 2860 (class 2604 OID 33018)
-- Name: edificio idedificio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edificio ALTER COLUMN idedificio SET DEFAULT nextval('public.edificio_id_seq'::regclass);


--
-- TOC entry 2862 (class 2604 OID 33047)
-- Name: espaciocultural idevento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.espaciocultural ALTER COLUMN idevento SET DEFAULT nextval('public.espaciocultural_idevento_seq'::regclass);


--
-- TOC entry 2863 (class 2604 OID 33048)
-- Name: espaciocultural idsala; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.espaciocultural ALTER COLUMN idsala SET DEFAULT nextval('public.espaciocultural_idsala_seq'::regclass);


--
-- TOC entry 2858 (class 2604 OID 33006)
-- Name: profesor idprofesor; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor ALTER COLUMN idprofesor SET DEFAULT nextval('public.profesor_id_seq'::regclass);


--
-- TOC entry 2861 (class 2604 OID 33029)
-- Name: salacultural idsala; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salacultural ALTER COLUMN idsala SET DEFAULT nextval('public.salacultural_idsala_seq'::regclass);


--
-- TOC entry 2998 (class 0 OID 32992)
-- Dependencies: 197
-- Data for Name: administrador; Type: TABLE DATA; Schema: public; Owner: postgres


--
-- TOC entry 3002 (class 0 OID 33015)
-- Dependencies: 201
-- Data for Name: edificio; Type: TABLE DATA; Schema: public; Owner: postgres
--

-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 196
-- Name: administrador_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.administrador_id_seq', 1, false);


--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 200
-- Name: edificio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.edificio_id_seq', 1, false);


--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 204
-- Name: espaciocultural_idevento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.espaciocultural_idevento_seq', 1, false);


--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 205
-- Name: espaciocultural_idsala_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.espaciocultural_idsala_seq', 1, false);


--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 198
-- Name: profesor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profesor_id_seq', 4, true);


--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 202
-- Name: salacultural_idsala_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.salacultural_idsala_seq', 1, false);


--
-- TOC entry 2865 (class 2606 OID 33000)
-- Name: administrador administrador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (idadministrador);


--
-- TOC entry 2869 (class 2606 OID 33023)
-- Name: edificio pk_edificios_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edificio
    ADD CONSTRAINT pk_edificios_id PRIMARY KEY (idedificio);


--
-- TOC entry 2873 (class 2606 OID 33053)
-- Name: espaciocultural pk_espaciocultural_idevento; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.espaciocultural
    ADD CONSTRAINT pk_espaciocultural_idevento PRIMARY KEY (idevento);


--
-- TOC entry 2871 (class 2606 OID 33034)
-- Name: salacultural pk_salacultural_idsala; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salacultural
    ADD CONSTRAINT pk_salacultural_idsala PRIMARY KEY (idsala);


--
-- TOC entry 2867 (class 2606 OID 33012)
-- Name: profesor profesor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor
    ADD CONSTRAINT profesor_pkey PRIMARY KEY (idprofesor);


--
-- TOC entry 2875 (class 2606 OID 33054)
-- Name: espaciocultural fk_espaciocultural; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.espaciocultural
    ADD CONSTRAINT fk_espaciocultural FOREIGN KEY (idsala) REFERENCES public.salacultural(idsala);


--
-- TOC entry 2874 (class 2606 OID 33035)
-- Name: salacultural fk_salacultural_edificio; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salacultural
    ADD CONSTRAINT fk_salacultural_edificio FOREIGN KEY (idedificio) REFERENCES public.edificio(idedificio);


-- Completed on 2019-03-17 17:38:29 CST

--
-- PostgreSQL database dump complete
--

