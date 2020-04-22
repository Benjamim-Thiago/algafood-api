ALTER TABLE restaurants ADD COLUMN kitchen_id BIGINT NOT NULL;

ALTER TABLE restaurants 
	ADD CONSTRAINT fk_kitchen
	FOREIGN KEY (kitchen_id) 
	REFERENCES kitchens(id);

	

INSERT INTO restaurants(name, delivery_fee, kitchen_id) VALUES ('THE BRAZIL', 7.50, 1);
INSERT INTO restaurants(name, delivery_fee, kitchen_id) VALUES ('MD THAY', 10, 2);