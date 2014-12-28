CREATE SEQUENCE account_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE account ALTER COLUMN id SET DEFAULT nextval('account_id_seq');

CREATE SEQUENCE equipment_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE equipment ALTER COLUMN id SET DEFAULT nextval('equipment_id_seq');

CREATE SEQUENCE exercise_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE exercise ALTER COLUMN id SET DEFAULT nextval('exercise_id_seq');

CREATE SEQUENCE plan_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE plan ALTER COLUMN id SET DEFAULT nextval('plan_id_seq');

CREATE SEQUENCE plan_exercise_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE plan_exercise ALTER COLUMN id SET DEFAULT nextval('plan_exercise_id_seq');