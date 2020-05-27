CREATE TABLE group_permission (
    group_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL
);

ALTER TABLE group_permission 
	ADD CONSTRAINT fk_permission_id
	FOREIGN KEY (permission_id) 
	REFERENCES users(id);

ALTER TABLE group_permission 
	ADD CONSTRAINT fk_group_id
	FOREIGN KEY (group_id) 
	REFERENCES groups(id);