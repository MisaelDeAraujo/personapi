package com.misael.personapi.repositories;

import com.misael.personapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Optional<Person> findByCompleteName(String s);
}
