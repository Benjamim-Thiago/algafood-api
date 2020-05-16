CREATE TABLE restaurant_payment_mode (
    restaurant_id BIGINT NOT NULL,
    payment_mode_id BIGINT NOT NULL
);
ALTER TABLE restaurant_payment_mode 
	ADD CONSTRAINT fk_restaurant_id
	FOREIGN KEY (restaurant_id) 
	REFERENCES restaurants(id);

ALTER TABLE restaurant_payment_mode 
	ADD CONSTRAINT fk_payment_mode_id
	FOREIGN KEY (payment_mode_id) 
	REFERENCES payment_modes(id);

INSERT INTO restaurant_payment_mode(restaurant_id, payment_mode_id) VALUES (1, 1);
INSERT INTO restaurant_payment_mode(restaurant_id, payment_mode_id) VALUES (1, 2);
INSERT INTO restaurant_payment_mode(restaurant_id, payment_mode_id) VALUES (1, 3);

INSERT INTO restaurant_payment_mode(restaurant_id, payment_mode_id) VALUES (2, 2);
INSERT INTO restaurant_payment_mode(restaurant_id, payment_mode_id) VALUES (3, 3);

 
