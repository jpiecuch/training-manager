CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE bars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE benches_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE dumbbells_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE exercises_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE loads_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE necks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE press_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE stands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE units_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE calendars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE exercise_bars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE exercise_benches_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE exercise_dumbbells_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE exercise_loads_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE exercise_necks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE exercise_press_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE exercise_stands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE day_exercises_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE calendars (
    id bigint DEFAULT nextval('calendars_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT calendars_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
    id bigint NOT NULL,
    name character varying(15) DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
    password character varying(255) NOT NULL,
    salt character varying(10) NOT NULL,
    first_name character varying(15) NOT NULL,
    last_name character varying(15) NOT NULL,
    calendar bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    version integer NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_calendar_fkey 
        FOREIGN KEY (calendar) REFERENCES calendars(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercises (
    id bigint DEFAULT nextval('exercises_id_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    movie_url character varying(255),
    description character varying,
    party_muscles integer NOT NULL,
    CONSTRAINT exercises_pkey PRIMARY KEY (id)
);

CREATE TABLE units (
    id bigint DEFAULT nextval('units_id_seq'::regclass) NOT NULL,
    name character varying(10) NOT NULL,
    description character varying(255),
    short_name character varying(5) NOT NULL,
    CONSTRAINT units_pkey PRIMARY KEY (id)
);

CREATE TABLE bars (
    id bigint DEFAULT nextval('bars_id_seq'::regclass) NOT NULL,
    handles_no integer NOT NULL,
    length_of double precision NOT NULL,
    length_of_unit bigint NOT NULL,
    strength double precision NOT NULL,
    strength_unit bigint NOT NULL,
    CONSTRAINT bars_pkey PRIMARY KEY (id),
    CONSTRAINT bars_length_of_unit_fkey 
        FOREIGN KEY (length_of_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT bars_strength_unit_fkey 
        FOREIGN KEY (strength_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE benches (
    id bigint DEFAULT nextval('benches_id_seq'::regclass) NOT NULL,
    length_of double precision NOT NULL,
    length_of_unit bigint NOT NULL,
    height double precision NOT NULL,
    height_unit bigint NOT NULL,
    type integer NOT NULL,
    CONSTRAINT benches_pkey PRIMARY KEY (id),
    CONSTRAINT benches_height_unit_fkey 
        FOREIGN KEY (height_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT benches_length_of_unit_fkey 
        FOREIGN KEY (length_of_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE dumbbells (
    id bigint DEFAULT nextval('dumbbells_id_seq'::regclass) NOT NULL,
    length_of double precision NOT NULL,
    length_of_unit bigint NOT NULL,
    weight double precision NOT NULL,
    weight_unit bigint NOT NULL,
    diameter double precision NOT NULL,
    diameter_unit bigint NOT NULL,
    connected_load boolean NOT NULL,
    CONSTRAINT dumbbells_pkey PRIMARY KEY (id),
    CONSTRAINT dumbbells_diameter_unit_fkey 
        FOREIGN KEY (diameter_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT dumbbells_length_of_unit_fkey 
        FOREIGN KEY (length_of_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT dumbbells_weight_unit_fkey 
        FOREIGN KEY (weight_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE press (
    id bigint DEFAULT nextval('press_id_seq'::regclass) NOT NULL,
    handles_no integer NOT NULL,
    strength double precision NOT NULL,
    strength_unit bigint NOT NULL,
    CONSTRAINT press_pkey PRIMARY KEY (id),
    CONSTRAINT press_strength_unit_fkey 
        FOREIGN KEY (strength_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE loads (
    id bigint DEFAULT nextval('loads_id_seq'::regclass) NOT NULL,
    weight double precision NOT NULL,
    hole_diameter double precision NOT NULL,
    weight_unit bigint NOT NULL,
    hole_diameter_unit bigint NOT NULL,
    CONSTRAINT loads_pkey PRIMARY KEY (id),
    CONSTRAINT loads_hole_diameter_unit_fkey 
        FOREIGN KEY (hole_diameter_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT loads_weight_unit_fkey 
        FOREIGN KEY (weight_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE necks (
    id bigint DEFAULT nextval('necks_id_seq'::regclass) NOT NULL,
    weight double precision NOT NULL,
    weight_unit bigint NOT NULL,
    diameter double precision NOT NULL,
    diameter_unit bigint NOT NULL,
    length_of double precision NOT NULL,
    length_of_unit bigint NOT NULL,
    connected_load boolean NOT NULL,
    type integer NOT NULL,
    CONSTRAINT necks_pkey PRIMARY KEY (id),
    CONSTRAINT necks_diameter_unit_fkey 
        FOREIGN KEY (diameter_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT necks_length_of_unit_fkey 
        FOREIGN KEY (length_of_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT necks_weight_unit_fkey 
        FOREIGN KEY (weight_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE stands (
    id bigint DEFAULT nextval('stands_id_seq'::regclass) NOT NULL,
    height_min double precision NOT NULL,
    height_min_unit bigint NOT NULL,
    height_max double precision NOT NULL,
    height_max_unit bigint NOT NULL,
    levels integer NOT NULL,
    CONSTRAINT stands_pkey PRIMARY KEY (id),
    CONSTRAINT stands_height_max_unit_fkey  
        FOREIGN KEY (height_max_unit) REFERENCES units(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT stands_height_min_unit_fkey 
        FOREIGN KEY (height_min_unit) REFERENCES units(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE day_exercises (
    id bigint DEFAULT nextval('day_exercises_id_seq'::regclass) NOT NULL,
    type bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    calendar bigint NOT NULL,
    "position" integer,
    series character varying,
    series_array character varying[],
    confirmed boolean NOT NULL,
    CONSTRAINT day_exercises_pkey PRIMARY KEY (id),
    CONSTRAINT exercises_calendar_fkey 
        FOREIGN KEY (calendar) REFERENCES calendars(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercises_type_fkey 
        FOREIGN KEY (type) REFERENCES exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_bars (
    id bigint DEFAULT nextval('exercise_bars_id_seq'::regclass) NOT NULL,
    equipment bigint NOT NULL,
    exercise bigint NOT NULL,
    CONSTRAINT exercise_bars_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_bars_bar_fkey 
        FOREIGN KEY (equipment) REFERENCES bars(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_bars_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_benches (
    id bigint DEFAULT nextval('exercise_benches_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_benches_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_benches_bench_fkey 
        FOREIGN KEY (equipment) REFERENCES benches(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_benches_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_dumbbells (
    id bigint DEFAULT nextval('exercise_dumbbells_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_dumbbells_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_dumbbells_dumbbell_fkey 
        FOREIGN KEY (equipment) REFERENCES dumbbells(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_dumbbells_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_loads (
    id bigint DEFAULT nextval('exercise_loads_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_loads_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_loads_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_loads_load_fkey 
        FOREIGN KEY (equipment) REFERENCES loads(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_necks (
    id bigint DEFAULT nextval('exercise_necks_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_necks_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_necks_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_necks_neck_fkey 
        FOREIGN KEY (equipment) REFERENCES necks(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_press (
    id bigint DEFAULT nextval('exercise_press_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_press_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_press_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_press_press_fkey 
        FOREIGN KEY (equipment) REFERENCES press(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE exercise_stands (
    id bigint DEFAULT nextval('exercise_stands_id_seq'::regclass) NOT NULL,
    exercise bigint NOT NULL,
    equipment bigint NOT NULL,
    CONSTRAINT exercise_stands_pkey PRIMARY KEY (id),
    CONSTRAINT exercise_stands_exercise_fkey 
        FOREIGN KEY (exercise) REFERENCES day_exercises(id)
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exercise_stands_stand_fkey 
        FOREIGN KEY (equipment) REFERENCES stands(id) 
        MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);