CREATE TABLE IF NOT EXISTS address_tb(
    id SERIAL PRIMARY KEY,
    public_place VARCHAR(60) NOT NULL,
    zip_code VARCHAR(8) NOT NULL,
    number VARCHAR(15) UNIQUE NOT NULL,
    city VARCHAR(60) NOT NULL,
    state VARCHAR(60) NOT NULL,
    address_type address_type
);