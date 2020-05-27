CREATE TABLE products 
(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR,
	price DECIMAL(10, 2) NOT NULL,
    active BOOLEAN NOT NULL,
	restaurant_id BIGINT NOT NULL    
);

ALTER TABLE products ADD CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id);