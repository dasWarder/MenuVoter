DELETE FROM menus_dishes;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO restaurant(id, name, description) VALUES
        (1, 'Monaco', 'The lux restaurant of Italian cousins'),
        (2, 'Wasabi', 'The restaurant of Japanese and European cousins');

INSERT INTO menu(id, rate, creating_date, restaurant_id) VALUES
        (3, 7.8, '2021-04-21 12:00:00', 1),
        (4, 6.7, '2021-04-12 13:00:00', 2);

INSERT INTO dish(id, title, description) VALUES
        (5, 'Pizza', 'The best classical pizza in the city'),
        (6, 'Sushi', 'The fresh set of sushi'),
        (7, 'Pasta', 'The pasta with sea'),
        (8, 'Yakitori', 'The hot yakitori');

INSERT INTO menus_dishes VALUES
        (3, 5),
        (3,7),
        (4, 6),
        (4, 8);
