package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.model.ScholifyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScholifyClassesRepository extends JpaRepository<ScholifyClass, Integer> {
}
