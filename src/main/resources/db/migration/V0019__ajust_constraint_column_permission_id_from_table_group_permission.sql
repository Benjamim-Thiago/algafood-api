ALTER TABLE public.group_permission DROP CONSTRAINT fk_permission_id;

ALTER TABLE group_permission
    ADD CONSTRAINT fk_permission_id FOREIGN KEY (permission_id)
    REFERENCES permissions (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;