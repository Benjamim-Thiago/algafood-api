CREATE TABLE payment_modes (
    id SERIAL PRIMARY KEY,
    description VARCHAR NOT NULL
);

INSERT INTO payment_modes(description)VALUES('ESPÉCIE');
INSERT INTO payment_modes(description)VALUES('CARTÃO CRÉDITO');
INSERT INTO payment_modes(description)VALUES('TRANSFERÊNCIA BANCÁRIA');
 
