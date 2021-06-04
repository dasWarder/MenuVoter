DELETE FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 3;

INSERT INTO restaurant(id, name, description) VALUES
        (1, 'Monaco', 'The lux restaurant of Italian cousins'),
        (2, 'Wasabi', 'The restaurant of Japanese and European cousins');

