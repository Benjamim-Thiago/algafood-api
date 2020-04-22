CREATE TABLE restaurants (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    delivery_fee NUMERIC(8,2) NOT NULL
);
