
--





--
-- TOC entry 222 (class 1259 OID 18496)
-- Name: Cupcake_bottom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cupcake_bottom" (
    bottom_id integer NOT NULL,
    bottom character varying NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE public."Cupcake_bottom" OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 18495)
-- Name: Cupcake_bottom_bottom_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cupcake_bottom_bottom_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Cupcake_bottom_bottom_id_seq" OWNER TO postgres;

--
-- TOC entry 3402 (class 0 OID 0)
-- Dependencies: 221
-- Name: Cupcake_bottom_bottom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cupcake_bottom_bottom_id_seq" OWNED BY public."Cupcake_bottom".bottom_id;


--
-- TOC entry 220 (class 1259 OID 18487)
-- Name: Cupcake_top; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cupcake_top" (
    top_id integer NOT NULL,
    topping character varying NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE public."Cupcake_top" OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 18486)
-- Name: Cupcake_top_top_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cupcake_top_top_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Cupcake_top_top_id_seq" OWNER TO postgres;

--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 219
-- Name: Cupcake_top_top_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cupcake_top_top_id_seq" OWNED BY public."Cupcake_top".top_id;


--
-- TOC entry 218 (class 1259 OID 18479)
-- Name: Order_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Order_details" (
    id integer NOT NULL,
    order_id integer NOT NULL,
    top integer NOT NULL,
    bottom integer NOT NULL,
    quantity integer NOT NULL,
    total_price numeric NOT NULL
);


ALTER TABLE public."Order_details" OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 18536)
-- Name: Order_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Order_details_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Order_details_id_seq" OWNER TO postgres;

--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 224
-- Name: Order_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Order_details_id_seq" OWNED BY public."Order_details".id;


--
-- TOC entry 217 (class 1259 OID 18469)
-- Name: Order_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Order_history" (
    order_id integer NOT NULL,
    user_id integer NOT NULL,
    date date NOT NULL
);


ALTER TABLE public."Order_history" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 18524)
-- Name: Order_history_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Order_history_order_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Order_history_order_id_seq" OWNER TO postgres;

--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 223
-- Name: Order_history_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Order_history_order_id_seq" OWNED BY public."Order_history".order_id;


--
-- TOC entry 216 (class 1259 OID 18459)
-- Name: User; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."User" (
    user_id integer NOT NULL,
    mail character varying NOT NULL,
    password character varying NOT NULL,
    amount numeric,
    role boolean DEFAULT false NOT NULL
);


ALTER TABLE public."User" OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 18458)
-- Name: User_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."User_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."User_id_seq" OWNER TO postgres;

--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 215
-- Name: User_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."User_id_seq" OWNED BY public."User".user_id;


--
-- TOC entry 3228 (class 2604 OID 18499)
-- Name: Cupcake_bottom bottom_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_bottom" ALTER COLUMN bottom_id SET DEFAULT nextval('public."Cupcake_bottom_bottom_id_seq"'::regclass);


--
-- TOC entry 3227 (class 2604 OID 18490)
-- Name: Cupcake_top top_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_top" ALTER COLUMN top_id SET DEFAULT nextval('public."Cupcake_top_top_id_seq"'::regclass);


--
-- TOC entry 3226 (class 2604 OID 18537)
-- Name: Order_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_details" ALTER COLUMN id SET DEFAULT nextval('public."Order_details_id_seq"'::regclass);


--
-- TOC entry 3225 (class 2604 OID 18525)
-- Name: Order_history order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_history" ALTER COLUMN order_id SET DEFAULT nextval('public."Order_history_order_id_seq"'::regclass);


--
-- TOC entry 3223 (class 2604 OID 18462)
-- Name: User user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User" ALTER COLUMN user_id SET DEFAULT nextval('public."User_id_seq"'::regclass);


--
-- TOC entry 3393 (class 0 OID 18496)
-- Dependencies: 222
-- Data for Name: Cupcake_bottom; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Cupcake_bottom" VALUES (1, 'Chocolate', 5.00);
INSERT INTO public."Cupcake_bottom" VALUES (2, 'Vanilla', 5.00);
INSERT INTO public."Cupcake_bottom" VALUES (3, 'Nutmeg', 5.00);
INSERT INTO public."Cupcake_bottom" VALUES (4, 'Pistacio', 6.00);
INSERT INTO public."Cupcake_bottom" VALUES (5, 'Almond', 7.00);


--
-- TOC entry 3391 (class 0 OID 18487)
-- Dependencies: 220
-- Data for Name: Cupcake_top; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Cupcake_top" VALUES (1, 'Chocolate', 5.00);
INSERT INTO public."Cupcake_top" VALUES (2, 'Blueberry', 5.00);
INSERT INTO public."Cupcake_top" VALUES (3, 'Raspberry', 5.00);
INSERT INTO public."Cupcake_top" VALUES (4, 'Crispy', 6.00);
INSERT INTO public."Cupcake_top" VALUES (5, 'Strawberry', 6.00);
INSERT INTO public."Cupcake_top" VALUES (6, 'Rum/Raisin', 7.00);
INSERT INTO public."Cupcake_top" VALUES (7, 'Orange', 8.00);
INSERT INTO public."Cupcake_top" VALUES (8, 'Lemon', 8.00);
INSERT INTO public."Cupcake_top" VALUES (9, 'Blue cheese', 9.00);



--
-- TOC entry 3238 (class 2606 OID 18503)
-- Name: Cupcake_bottom Cupcake_bottom_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_bottom"
    ADD CONSTRAINT "Cupcake_bottom_pkey" PRIMARY KEY (bottom_id);


--
-- TOC entry 3236 (class 2606 OID 18494)
-- Name: Cupcake_top Cupcake_top_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_top"
    ADD CONSTRAINT "Cupcake_top_pkey" PRIMARY KEY (top_id);


--
-- TOC entry 3234 (class 2606 OID 18544)
-- Name: Order_details Order_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_details"
    ADD CONSTRAINT "Order_details_pkey" PRIMARY KEY (id);


--
-- TOC entry 3232 (class 2606 OID 18530)
-- Name: Order_history Order_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_history"
    ADD CONSTRAINT "Order_history_pkey" PRIMARY KEY (order_id);


--
-- TOC entry 3230 (class 2606 OID 18466)
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (user_id);


--
-- TOC entry 3240 (class 2606 OID 18555)
-- Name: Order_details Order_details_bottom_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_details"
    ADD CONSTRAINT "Order_details_bottom_fkey" FOREIGN KEY (bottom) REFERENCES public."Cupcake_bottom"(bottom_id) NOT VALID;


--
-- TOC entry 3241 (class 2606 OID 18545)
-- Name: Order_details Order_details_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_details"
    ADD CONSTRAINT "Order_details_order_id_fkey" FOREIGN KEY (order_id) REFERENCES public."Order_history"(order_id) NOT VALID;


--
-- TOC entry 3242 (class 2606 OID 18550)
-- Name: Order_details Order_details_top_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_details"
    ADD CONSTRAINT "Order_details_top_fkey" FOREIGN KEY (top) REFERENCES public."Cupcake_top"(top_id) NOT VALID;


--
-- TOC entry 3239 (class 2606 OID 18531)
-- Name: Order_history Order_history_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order_history"
    ADD CONSTRAINT "Order_history_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public."User"(user_id) NOT VALID;


-- Completed on 2025-03-25 11:56:30 UTC

--
-- PostgreSQL database dump complete
--

