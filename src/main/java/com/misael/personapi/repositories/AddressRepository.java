package com.misael.personapi.repositories;

import com.misael.personapi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    Optional<Address> findByZipCode(String zipCode);
    Optional<Address> findByNumber(String number);

}
