COMMENT ON TABLE pets IS 'The pets table. Contains the pets of the store';

COMMENT ON COLUMN pets.id IS 'The unique identifier of the pet';
COMMENT ON COLUMN pets.name IS 'The name of the pet';
COMMENT ON COLUMN pets.category_id IS 'The category of the pet. Refer to categories table';
COMMENT ON COLUMN pets.photo_urls IS 'The photo urls list of the pet';
COMMENT ON COLUMN pets.status IS 'The status of the pet (pending, available, sold)';

CREATE TABLE IF NOT EXISTS orders (
  id          SERIAL PRIMARY KEY,
  pet_id      BIGINT      NOT NULL,
  quantity    INTEGER     NOT NULL,
  ship_date   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status      VARCHAR(10) NOT NULL,
  completed   BOOLEAN NOT NULL DEFAULT FALSE,
  created_by  VARCHAR(50) NOT NULL,
  created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by  VARCHAR(50) NOT NULL,
  updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

alter table orders ADD CONSTRAINT fk_orders_pet_id FOREIGN KEY (pet_id) REFERENCES pets(id);

COMMENT ON TABLE orders IS 'The order table. Contains the orders of the pets';

COMMENT ON COLUMN orders.id IS 'The unique identifier of the order';
COMMENT ON COLUMN orders.pet_id IS 'The identifier of the pet. Refer to pets table';
COMMENT ON COLUMN orders.quantity IS 'The quantity of the pet';
COMMENT ON COLUMN orders.ship_date IS 'The date when the order was shipped';
COMMENT ON COLUMN orders.status IS 'The status of the order (placed, approved, delivered)';
COMMENT ON COLUMN orders.completed IS 'The completed status of the order';

COMMENT ON CONSTRAINT fk_orders_pet_id ON orders IS 'The foreign key constraint on pet_id';
