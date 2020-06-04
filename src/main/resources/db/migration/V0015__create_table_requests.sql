CREATE TABLE requests(
	id BIGSERIAL PRIMARY KEY,
	
	restaurant_id BIGINT NOT NULL,
	
	client_id BIGINT NOT NULL,
	
	address_zipcode VARCHAR(8) NOT NULL,
	address_public_area VARCHAR(100) NOT NULL,
	address_number VARCHAR(10) NOT NULL,
	address_complement VARCHAR(15),
	address_neighborhood VARCHAR(30) NOT NULL,
	
	address_city_id BIGINT NOT NULL,
	
	payment_mode_id BIGINT NOT NULL,
	
	sub_total NUMERIC(10,2) NOT NULL,
	delivery_fee NUMERIC(10,2) NOT NULL,
	value_total NUMERIC(10,2) NOT NULL,
	created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	confirmation_date TIMESTAMP WITHOUT TIME ZONE,
	cancellation_date TIMESTAMP WITHOUT TIME ZONE,
	delivery_date TIMESTAMP WITHOUT TIME ZONE,
	status VARCHAR(10) NOT NULL
);

ALTER TABLE requests 
	ADD CONSTRAINT fk_restaurant
	FOREIGN KEY (restaurant_id) 
	REFERENCES restaurants(id);
	
ALTER TABLE requests 
	ADD CONSTRAINT fk_client
	FOREIGN KEY (client_id) 
	REFERENCES users(id);

ALTER TABLE requests 
	ADD CONSTRAINT fk_city
	FOREIGN KEY (address_city_id) 
	REFERENCES cities(id);
	
ALTER TABLE requests 
	ADD CONSTRAINT fk_payment_mode
	FOREIGN KEY (payment_mode_id) 
	REFERENCES payment_modes(id);
	
