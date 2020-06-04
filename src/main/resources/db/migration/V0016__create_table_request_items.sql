CREATE TABLE request_items(
	id BIGSERIAL PRIMARY KEY,
	
	request_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	
	amount SMALLINT NOT NULL,
	price NUMERIC(10, 2) NOT NULL,
	total NUMERIC(10, 2) NOT NULL,
	comment VARCHAR(255) NOT NULL
);


ALTER TABLE request_items 
	ADD CONSTRAINT fk_request
	FOREIGN KEY (request_id) 
	REFERENCES requests(id);
	
ALTER TABLE request_items 
	ADD CONSTRAINT fk_product
	FOREIGN KEY (product_id) 
	REFERENCES products(id);
