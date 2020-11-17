create table product_photo (
    product_id BIGINT UNIQUE NOT NULL,
    file_name VARCHAR(150) NOT NULL,
    description VARCHAR(150),
    content_type VARCHAR(80) NOT NULL,
    size INT NOT NULL
);

alter table product_photo 
	add constraint fk_product
	foreign key (product_id) references products (id);
