CREATE TABLE IF NOT EXISTS persons_address_tb(
    id SERIAL PRIMARY KEY,
    person_id INT NOT NULL,
    address_id INT NOT NULL
);

