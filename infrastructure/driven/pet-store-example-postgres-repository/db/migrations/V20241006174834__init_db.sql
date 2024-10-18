DROP TABLE IF EXISTS pets;
DROP TYPE IF EXISTS pet_status;
CREATE TABLE IF NOT EXISTS pets
(
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(255)   NOT NULL,
  category_id   BIGINT         NOT NULL,
  photo_urls    TEXT[] NOT     NULL,
  status        VARCHAR(25)    NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT categories_name_unique UNIQUE (name)
);

ALTER TABLE pets ADD CONSTRAINT fk_pets_category_id
  FOREIGN KEY (category_id) REFERENCES categories (id);

CREATE TABLE IF NOT EXISTS tags
(
  id      SERIAL PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  CONSTRAINT tags_name_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS pets_tags
(
  pet_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  CONSTRAINT fk_pets_tags_pet_id FOREIGN KEY (pet_id) REFERENCES pets (id),
  CONSTRAINT fk_pets_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id),
  CONSTRAINT pk_pets_tags_pet_id_tag_id PRIMARY KEY (pet_id, tag_id)
);
