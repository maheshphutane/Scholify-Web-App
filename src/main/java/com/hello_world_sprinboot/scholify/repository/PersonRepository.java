package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person , Integer> {

    Person getByEmail(String email);

}
