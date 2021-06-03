DROP TABLE IF EXISTS menus_dishes;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE restaurant (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

CREATE UNIQUE INDEX id_name_idx ON restaurant(id, name);

CREATE TABLE menu (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('global_seq'),
    rate FLOAT,
    creating_date TIMESTAMP NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE UNIQUE INDEX rest_id_date ON menu(restaurant_id, creating_date);

CREATE TABLE dish (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('global_seq'),
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE menus_dishes (
    menu_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu(id),
    FOREIGN KEY (dish_id) REFERENCES dish(id)
);

CREATE UNIQUE INDEX menu_dish_idx ON menus_dishes(menu_id, dish_id);