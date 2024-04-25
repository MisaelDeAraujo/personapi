ALTER TABLE persons_address_tb
ADD CONSTRAINT fk_person_id FOREIGN KEY (person_id) REFERENCES person_tb(id);

ALTER TABLE persons_address_tb
ADD CONSTRAINT fk_address_id FOREIGN KEY (address_id) REFERENCES address_tb(id);