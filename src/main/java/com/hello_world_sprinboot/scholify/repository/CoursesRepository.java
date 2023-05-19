package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.model.Courses;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "courses")
public interface CoursesRepository extends JpaRepository<Courses,Integer> {
    /*
   Spring Data JPA allows us to apply static sorting by adding the OrderBy keyword
   to the method name along with the property name and sort direction (Asc or Desc).
   * */
    List<Courses> findByOrderByNameDesc();

    /*
    The Asc keyword is optional as OrderBy, by default,
    sorts the results in the ascending order.
    * */
    List<Courses> findByOrderByName();

    List<Courses> findAll(Sort and);
}
