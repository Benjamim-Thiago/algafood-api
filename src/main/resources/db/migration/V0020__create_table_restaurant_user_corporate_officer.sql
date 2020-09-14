create table restaurant_user_corporate_officer (
    restaurant_id bigint not null,
    user_id bigint not null,
    
    primary key (restaurant_id, user_id)
);

alter table restaurant_user_corporate_officer 
	add constraint fk_restaurant
	foreign key (restaurant_id) references restaurants (id);

alter table restaurant_user_corporate_officer 
	add constraint fk_user
	foreign key (user_id) references users (id);