DELETE FROM customer;

ALTER SEQUENCE users_seq RESTART WITH 100000;

INSERT INTO customer(email, voted) VALUES
('alex@gmail.com', false),
('james@gmail.com', false);
