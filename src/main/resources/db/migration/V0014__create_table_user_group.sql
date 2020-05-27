CREATE TABLE user_group (
    user_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL
);

ALTER TABLE user_group 
	ADD CONSTRAINT fk_user_id
	FOREIGN KEY (user_id) 
	REFERENCES users(id);

ALTER TABLE user_group 
	ADD CONSTRAINT fk_group_id
	FOREIGN KEY (group_id) 
	REFERENCES groups(id);