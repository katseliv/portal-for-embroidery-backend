ALTER TABLE embroidery.files
    DROP COLUMN folder_id;

ALTER TABLE embroidery.designs
    ADD COLUMN folder_id INTEGER REFERENCES embroidery.folders (id) ON DELETE CASCADE;