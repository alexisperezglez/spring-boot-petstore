ALTER TABLE orders
  ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE orders
  ALTER COLUMN updated_by DROP NOT NULL;