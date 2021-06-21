DROP TABLE IF EXISTS restaurant;

CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

CREATE UNIQUE INDEX id_name_idx ON restaurant(id, name);
