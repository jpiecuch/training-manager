CREATE TABLE account (
  id          BIGINT PRIMARY KEY                        NOT NULL,
  name        CHARACTER VARYING(50)                     NOT NULL,
  password    CHARACTER VARYING(255)                    NOT NULL,
  salt        CHARACTER VARYING(20)                     NOT NULL,
  created     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  updated     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  status      INTEGER DEFAULT 0                         NOT NULL,
  email       CHARACTER VARYING(50),
  config      CHARACTER VARYING(50000),
  provider    INTEGER                                   NOT NULL,
  social_type INTEGER                                   NOT NULL
);

CREATE TABLE role (
  id          BIGINT PRIMARY KEY    NOT NULL,
  name        CHARACTER VARYING(50) NOT NULL,
  permissions CHARACTER VARYING(255),
  modifiable  BOOLEAN               NOT NULL
);

CREATE TABLE account_role (
  account BIGINT NOT NULL,
  role    BIGINT NOT NULL
);

CREATE TABLE equipment (
  id       BIGINT PRIMARY KEY NOT NULL,
  type     INTEGER            NOT NULL,
  data     CHARACTER VARYING(50000),
  length   INTEGER,
  weight   DOUBLE PRECISION,
  strength DOUBLE PRECISION
);

CREATE TABLE description (
  id            BIGINT PRIMARY KEY     NOT NULL,
  name          CHARACTER VARYING(255) NOT NULL,
  movie_url     CHARACTER VARYING(255),
  description   CHARACTER VARYING(50000),
  party_muscles INTEGER                NOT NULL,
  type          INTEGER                NOT NULL,
  equipment     INTEGER,
  level         INTEGER                NOT NULL,
  mechanics     INTEGER                NOT NULL,
  force         INTEGER                NOT NULL,
  sets          INTEGER                NOT NULL,
  sides         INTEGER                NOT NULL
);

CREATE TABLE plan (
  id      BIGINT PRIMARY KEY    NOT NULL,
  name    CHARACTER VARYING(50) NOT NULL,
  goal    INTEGER               NOT NULL,
  creator BIGINT                NOT NULL,
  used    BOOLEAN               NOT NULL
);

CREATE TABLE phase (
  id          BIGINT PRIMARY KEY NOT NULL,
  position    INTEGER            NOT NULL,
  goal        INTEGER            NOT NULL,
  description CHARACTER VARYING(50000),
  plan        BIGINT,
  weeks       INTEGER            NOT NULL
);

CREATE TABLE workout (
  id       BIGINT PRIMARY KEY      NOT NULL,
  week_day INTEGER                 NOT NULL,
  muscles  CHARACTER VARYING(1024) NOT NULL,
  position INTEGER                 NOT NULL,
  phase    BIGINT
);

CREATE TABLE exercise (
  id          BIGINT PRIMARY KEY    NOT NULL,
  workout     BIGINT,
  description BIGINT                NOT NULL,
  position    INTEGER               NOT NULL,
  super_set   INTEGER               NOT NULL,
  reps        CHARACTER VARYING(50) NOT NULL
);

CREATE TABLE userconnection (
  userid         CHARACTER VARYING(255)  NOT NULL,
  providerid     CHARACTER VARYING(255)  NOT NULL,
  provideruserid CHARACTER VARYING(255)  NOT NULL,
  rank           INTEGER                 NOT NULL,
  displayname    CHARACTER VARYING(255),
  profileurl     CHARACTER VARYING(512),
  imageurl       CHARACTER VARYING(512),
  accesstoken    CHARACTER VARYING(1024) NOT NULL,
  secret         CHARACTER VARYING(255),
  refreshtoken   CHARACTER VARYING(255),
  expiretime     BIGINT
);

CREATE TABLE user_workout (
  id      BIGINT PRIMARY KEY                        NOT NULL,
  comment CHARACTER VARYING(50000),
  remind  BOOLEAN DEFAULT FALSE,
  date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  account BIGINT                                    NOT NULL,
  workout BIGINT                                    NOT NULL,
  state   INTEGER                                   NOT NULL
);

CREATE TABLE execution (
  id       BIGINT PRIMARY KEY    NOT NULL,
  reps     CHARACTER VARYING(50),
  weights  CHARACTER VARYING(50),
  exercise BIGINT                NOT NULL,
  confirm  BOOLEAN DEFAULT FALSE NOT NULL,
  comment  CHARACTER VARYING(50000),
  workout  BIGINT                NOT NULL,
  state    INTEGER               NOT NULL,
  result   CHARACTER VARYING(1024)
);

CREATE TABLE account_record (
  id      BIGINT PRIMARY KEY                        NOT NULL,
  type    INTEGER                                   NOT NULL,
  value   CHARACTER VARYING(50),
  date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  account BIGINT                                    NOT NULL
);

ALTER TABLE userconnection ADD CONSTRAINT userconnection_pkey PRIMARY KEY (userid, providerid, provideruserid);

ALTER TABLE account ADD CONSTRAINT account_email_unique UNIQUE (email, social_type);
ALTER TABLE account ADD CONSTRAINT account_name_unique UNIQUE (name, social_type);

ALTER TABLE role ADD CONSTRAINT role_name_unique UNIQUE (name);

ALTER TABLE exercise ADD CONSTRAINT description_fkey FOREIGN KEY (description) REFERENCES description (id);
ALTER TABLE exercise ADD CONSTRAINT workout_fkey FOREIGN KEY (workout) REFERENCES workout (id);
ALTER TABLE plan ADD CONSTRAINT creator_fkey FOREIGN KEY (creator) REFERENCES account (id);
ALTER TABLE phase ADD CONSTRAINT plan_fkey FOREIGN KEY (plan) REFERENCES plan (id);
ALTER TABLE workout ADD CONSTRAINT phase_fkey FOREIGN KEY (phase) REFERENCES phase (id);
ALTER TABLE execution ADD CONSTRAINT exercise_fkey FOREIGN KEY (exercise) REFERENCES exercise (id);
ALTER TABLE execution ADD CONSTRAINT execution_user_workout_fkey FOREIGN KEY (workout) REFERENCES user_workout (id);
ALTER TABLE user_workout ADD CONSTRAINT account_fkey FOREIGN KEY (account) REFERENCES account (id);
ALTER TABLE user_workout ADD CONSTRAINT workout_user_fkey FOREIGN KEY (workout) REFERENCES workout (id);
ALTER TABLE account_role ADD CONSTRAINT account_role_role_fkey FOREIGN KEY (role) REFERENCES role (id);
ALTER TABLE account_role ADD CONSTRAINT account_role_account_fkey FOREIGN KEY (account) REFERENCES account (id);
ALTER TABLE account_record ADD CONSTRAINT account_record_account_fkey FOREIGN KEY (account) REFERENCES account (id);