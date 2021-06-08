DROP TABLE IF EXISTS customer;
DROP SEQUENCE IF EXISTS users_seq;

CREATE SEQUENCE users_seq START WITH 100000;

CREATE TABLE customer (
                id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('users_seq'),
                email VARCHAR(255) NOT NULL,
                voted BOOLEAN
);


