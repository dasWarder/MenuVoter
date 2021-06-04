DROP TABLE IF EXISTS restaurant;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 3;

CREATE TABLE restaurant (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

CREATE UNIQUE INDEX id_name_idx ON restaurant(id, name);
