ALTER TABLE requests
    ADD COLUMN code character varying(36) UNIQUE;

UPDATE requests SET code = '32144624-62e2-49fe-bcf2-1487e4ab66c4' WHERE id=1;

ALTER TABLE requests
    ALTER COLUMN code SET NOT NULL;