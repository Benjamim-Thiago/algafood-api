ALTER TABLE restaurants ADD COLUMN address_zipcode VARCHAR(8);
ALTER TABLE restaurants ADD COLUMN address_public_area VARCHAR(100);
ALTER TABLE restaurants ADD COLUMN address_number VARCHAR(10);
ALTER TABLE restaurants ADD COLUMN address_complement VARCHAR(15);
ALTER TABLE restaurants ADD COLUMN address_neighborhood VARCHAR(30);
ALTER TABLE restaurants ADD COLUMN address_city_id BIGINT;

ALTER TABLE restaurants 
	ADD CONSTRAINT fk_city
	FOREIGN KEY (address_city_id) 
	REFERENCES cities(id);
