DELETE FROM customer;

ALTER SEQUENCE users_seq RESTART WITH 100000;

INSERT INTO customer(id, email, voted) VALUES
(100000, 'alex@gmail.com', false),
(100001, 'james@gmail.com', false);
