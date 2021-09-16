CREATE TABLE IF NOT EXISTS token(id BIGSERIAL PRIMARY KEY, token VARCHAR(255), expiration_dt TIMESTAMP);
CREATE INDEX IF NOT EXISTS token_expiration_dt_idx ON token(expiration_dt);
CREATE TABLE IF NOT EXISTS realm(id BIGSERIAL PRIMARY KEY, blizzard_id BIGINT, en_name VARCHAR(255), ru_name VARCHAR(255));
CREATE TABLE IF NOT EXISTS item(id BIGSERIAL PRIMARY KEY, blizzard_id BIGINT, en_name VARCHAR(255), ru_name VARCHAR(255));
CREATE INDEX IF NOT EXISTS item_blizzard_id_idx ON item(blizzard_id);
CREATE TABLE IF NOT EXISTS auction(id BIGSERIAL PRIMARY KEY, blizzard_id BIGINT, realm_id BIGINT, item_blizzard_id BIGINT, price BIGINT, quantity BIGINT, local_datetime TIMESTAMP);
CREATE INDEX IF NOT EXISTS auction_realm_id_idx ON auction(realm_id);
CREATE INDEX IF NOT EXISTS auction_local_datetime_idx ON auction(local_datetime);
