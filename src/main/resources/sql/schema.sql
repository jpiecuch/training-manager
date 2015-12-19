DROP SCHEMA public;

CREATE TABLE account (
    id bigint PRIMARY KEY NOT NULL,
    name character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    salt character varying(20) NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone DEFAULT now() NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    email character varying(50),
    config character varying(50000),
    provider integer NOT NULL,
    social_type integer NOT NULL
);

CREATE TABLE role (
  id bigint PRIMARY KEY NOT NULL,
  name character varying(50) NOT NULL,
  permissions character varying(255),
  modifiable boolean NOT NULL
);

CREATE TABLE account_role (
  account bigint NOT NULL,
  role bigint NOT NULL
);

CREATE TABLE equipment (
    id bigint PRIMARY KEY NOT NULL,
    type integer NOT NULL,
    data character varying(50000),
    length integer,
    weight double precision,
    strength double precision
);

CREATE TABLE description (
    id bigint PRIMARY KEY NOT NULL,
    name character varying(255) NOT NULL,
    movie_url character varying(255),
    description character varying(50000),
    party_muscles integer NOT NULL,
    type integer NOT NULL,
    equipment integer,
    level integer NOT NULL,
    mechanics integer NOT NULL,
    force integer NOT NULL,
    sets integer NOT NULL,
    sides integer NOT NULL
);

CREATE TABLE plan (
    id bigint PRIMARY KEY NOT NULL,
    name character varying(50) NOT NULL,
    goal integer NOT NULL,
    creator bigint NOT NULL,
    used boolean NOT NULL
);

CREATE TABLE phase (
    id bigint PRIMARY KEY NOT NULL,
    position integer NOT NULL,
    goal integer NOT NULL,
    description character varying(50000),
    plan bigint,
    weeks integer NOT NULL
);

CREATE TABLE workout (
    id bigint PRIMARY KEY NOT NULL,
    week_day integer NOT NULL,
    muscles character varying(1024) NOT NULL,
    position integer NOT NULL,
    phase bigint
);

CREATE TABLE exercise (
    id bigint PRIMARY KEY NOT NULL,
    workout bigint,
    description bigint NOT NULL,
    position integer NOT NULL,
    super_set integer NOT NULL,
    reps character varying(50) NOT NULL
);

CREATE TABLE userconnection (
    userid character varying(255) NOT NULL,
    providerid character varying(255) NOT NULL,
    provideruserid character varying(255) NOT NULL,
    rank integer NOT NULL,
    displayname character varying(255),
    profileurl character varying(512),
    imageurl character varying(512),
    accesstoken character varying(1024) NOT NULL,
    secret character varying(255),
    refreshtoken character varying(255),
    expiretime bigint
);

CREATE TABLE user_workout (
    id BIGINT PRIMARY KEY NOT NULL,
    comment character varying(50000),
    remind boolean DEFAULT false,
    date timestamp without time zone DEFAULT now() NOT NULL,
    account bigint NOT NULL,
    workout BIGINT NOT NULL,
    state integer NOT NULL
);

CREATE TABLE execution (
    id BIGINT PRIMARY KEY NOT NULL,
    reps character varying(50),
    weights character varying(50),
    exercise bigint NOT NULL,
    confirm boolean DEFAULT false NOT NULL,
    comment character varying(50000),
    workout BIGINT NOT NULL,
    state integer NOT NULL,
    result character varying(1024)
);

CREATE TABLE account_record (
    id BIGINT PRIMARY KEY NOT NULL,
    type integer NOT NULL,
    value character varying(50),
    date timestamp without time zone DEFAULT now() NOT NULL,
    account bigint NOT NULL
);

ALTER TABLE userconnection ADD CONSTRAINT userconnection_pkey PRIMARY KEY (userid, providerid, provideruserid);

ALTER TABLE account ADD CONSTRAINT account_email_unique UNIQUE (email, social_type);
ALTER TABLE account ADD CONSTRAINT account_name_unique UNIQUE (name, social_type);

ALTER TABLE role ADD CONSTRAINT role_name_unique UNIQUE (name);

ALTER TABLE exercise ADD CONSTRAINT description_fkey FOREIGN KEY (description) REFERENCES description(id);
ALTER TABLE exercise ADD CONSTRAINT workout_fkey FOREIGN KEY (workout) REFERENCES workout(id);
ALTER TABLE plan ADD CONSTRAINT creator_fkey FOREIGN KEY (creator) REFERENCES account(id);
ALTER TABLE phase ADD CONSTRAINT plan_fkey FOREIGN KEY (plan) REFERENCES plan(id);
ALTER TABLE workout ADD CONSTRAINT phase_fkey FOREIGN KEY (phase) REFERENCES phase(id);
ALTER TABLE execution ADD CONSTRAINT exercise_fkey FOREIGN KEY (exercise) REFERENCES exercise(id);
ALTER TABLE execution ADD CONSTRAINT execution_user_workout_fkey FOREIGN KEY (workout) REFERENCES user_workout(id);
ALTER TABLE user_workout ADD CONSTRAINT account_fkey FOREIGN KEY (account) REFERENCES account(id);
ALTER TABLE user_workout ADD CONSTRAINT workout_user_fkey FOREIGN KEY (workout) REFERENCES workout(id);
ALTER TABLE account_role ADD CONSTRAINT account_role_role_fkey FOREIGN KEY (role) REFERENCES role(id);
ALTER TABLE account_role ADD CONSTRAINT account_role_account_fkey FOREIGN KEY (account) REFERENCES account(id);
ALTER TABLE account_record ADD CONSTRAINT account_record_account_fkey FOREIGN KEY (account) REFERENCES account(id);