--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2025-07-11 13:45:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 221 (class 1259 OID 16704)
-- Name: laptop; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.laptop (
    code integer NOT NULL,
    model character varying(50) NOT NULL,
    speed smallint NOT NULL,
    ram smallint NOT NULL,
    hd real NOT NULL,
    screen smallint NOT NULL,
    price money NOT NULL
);


ALTER TABLE public.laptop OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16703)
-- Name: laptop_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.laptop_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.laptop_code_seq OWNER TO postgres;

--
-- TOC entry 4883 (class 0 OID 0)
-- Dependencies: 220
-- Name: laptop_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.laptop_code_seq OWNED BY public.laptop.code;


--
-- TOC entry 219 (class 1259 OID 16692)
-- Name: pc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pc (
    code integer NOT NULL,
    model character varying(50) NOT NULL,
    speed smallint NOT NULL,
    ram smallint NOT NULL,
    hd real NOT NULL,
    cd character varying(10) NOT NULL,
    price money NOT NULL
);


ALTER TABLE public.pc OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16691)
-- Name: pc_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pc_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pc_code_seq OWNER TO postgres;

--
-- TOC entry 4884 (class 0 OID 0)
-- Dependencies: 218
-- Name: pc_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pc_code_seq OWNED BY public.pc.code;


--
-- TOC entry 223 (class 1259 OID 16716)
-- Name: printer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.printer (
    code integer NOT NULL,
    model character varying(50) NOT NULL,
    color character(1),
    type character varying(10),
    price money NOT NULL,
    CONSTRAINT printer_color_check CHECK ((color = ANY (ARRAY['y'::bpchar, 'n'::bpchar]))),
    CONSTRAINT printer_type_check CHECK (((type)::text = ANY ((ARRAY['Laser'::character varying, 'Jet'::character varying, 'Matrix'::character varying])::text[])))
);


ALTER TABLE public.printer OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16715)
-- Name: printer_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.printer_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.printer_code_seq OWNER TO postgres;

--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 222
-- Name: printer_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.printer_code_seq OWNED BY public.printer.code;


--
-- TOC entry 217 (class 1259 OID 16685)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    model character varying(50) NOT NULL,
    maker character varying(10) NOT NULL,
    type character varying(50),
    CONSTRAINT product_type_check CHECK (((type)::text = ANY ((ARRAY['PC'::character varying, 'Laptop'::character varying, 'Printer'::character varying])::text[])))
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 4710 (class 2604 OID 16707)
-- Name: laptop code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laptop ALTER COLUMN code SET DEFAULT nextval('public.laptop_code_seq'::regclass);


--
-- TOC entry 4709 (class 2604 OID 16695)
-- Name: pc code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pc ALTER COLUMN code SET DEFAULT nextval('public.pc_code_seq'::regclass);


--
-- TOC entry 4711 (class 2604 OID 16719)
-- Name: printer code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer ALTER COLUMN code SET DEFAULT nextval('public.printer_code_seq'::regclass);


--
-- TOC entry 4875 (class 0 OID 16704)
-- Dependencies: 221
-- Data for Name: laptop; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.laptop (code, model, speed, ram, hd, screen, price) FROM stdin;
1	M1002	2400	8	256	15	650,00 ?
2	M1004	2200	4	128	13	520,00 ?
3	M1010	3200	16	512	16	1 500,00 ?
4	B1002	2300	16	512	15	1 200,00 ?
5	G9001	2000	8	256	14	999,99 ?
\.


--
-- TOC entry 4873 (class 0 OID 16692)
-- Dependencies: 219
-- Data for Name: pc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pc (code, model, speed, ram, hd, cd, price) FROM stdin;
1	M1001	3200	8	500	8x	450,00 ?
2	M1005	2800	4	250	4x	350,00 ?
3	M1020	2800	8	500	12x	580,00 ?
4	B1001	2500	8	400	8x	480,00 ?
5	A2001	3000	16	512	12x	700,00 ?
6	A2002	2500	8	256	8x	550,00 ?
7	A2003	2200	4	128	4x	400,00 ?
8	PC1	3000	4	500	8x	700,00 ?
9	PC2	3500	4	400	12x	750,00 ?
10	PC3	2500	8	600	8x	800,00 ?
11	PC4	2800	16	512	24x	900,00 ?
\.


--
-- TOC entry 4877 (class 0 OID 16716)
-- Dependencies: 223
-- Data for Name: printer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.printer (code, model, color, type, price) FROM stdin;
1	M1003	y	Jet	120,00 ?
2	M1006	n	Laser	95,00 ?
3	B1003	n	Laser	110,00 ?
4	PR1	y	Laser	150,00 ?
5	PR2	n	Jet	100,00 ?
6	PR3	y	Matrix	120,00 ?
\.


--
-- TOC entry 4871 (class 0 OID 16685)
-- Dependencies: 217
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (model, maker, type) FROM stdin;
M1001	Dell	PC
M1002	Dell	Laptop
M1003	HP	Printer
M1004	Asus	Laptop
M1005	Lenovo	PC
M1006	Canon	Printer
M1010	Apple	Laptop
M1020	Acer	PC
B1001	B	PC
B1002	B	Laptop
B1003	B	Printer
A2001	A	PC
A2002	A	PC
A2003	A	PC
G9001	GPD	Laptop
PC1	Maker1	PC
PC2	Maker1	PC
PC3	Maker2	PC
PC4	Maker3	PC
PR1	Maker1	Printer
PR2	Maker2	Printer
PR3	Maker3	Printer
\.


--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 220
-- Name: laptop_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.laptop_code_seq', 5, true);


--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 218
-- Name: pc_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pc_code_seq', 11, true);


--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 222
-- Name: printer_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.printer_code_seq', 6, true);


--
-- TOC entry 4720 (class 2606 OID 16709)
-- Name: laptop laptop_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laptop
    ADD CONSTRAINT laptop_pkey PRIMARY KEY (code);


--
-- TOC entry 4718 (class 2606 OID 16697)
-- Name: pc pc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pc
    ADD CONSTRAINT pc_pkey PRIMARY KEY (code);


--
-- TOC entry 4722 (class 2606 OID 16723)
-- Name: printer printer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_pkey PRIMARY KEY (code);


--
-- TOC entry 4716 (class 2606 OID 16690)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (model);


--
-- TOC entry 4724 (class 2606 OID 16710)
-- Name: laptop laptop_model_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laptop
    ADD CONSTRAINT laptop_model_fkey FOREIGN KEY (model) REFERENCES public.product(model) ON DELETE CASCADE;


--
-- TOC entry 4723 (class 2606 OID 16698)
-- Name: pc pc_model_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pc
    ADD CONSTRAINT pc_model_fkey FOREIGN KEY (model) REFERENCES public.product(model) ON DELETE CASCADE;


--
-- TOC entry 4725 (class 2606 OID 16724)
-- Name: printer printer_model_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_model_fkey FOREIGN KEY (model) REFERENCES public.product(model) ON DELETE CASCADE;


-- Completed on 2025-07-11 13:45:17

--
-- PostgreSQL database dump complete
--

