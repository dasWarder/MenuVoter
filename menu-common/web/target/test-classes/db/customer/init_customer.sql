DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          email VARCHAR(255) NOT NULL,
                          voted BOOLEAN
);
