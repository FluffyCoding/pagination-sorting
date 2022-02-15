package com.unity.paginationsorting.dao;

import com.unity.paginationsorting.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {

    @Query(value = "SELECT * FROM actor WHERE first_name like ?1%  \n-- #pageable\n",
    countQuery = "SELECT COUNT(*) FROM actor WHERE first_name like ?1%",
    nativeQuery = true)
    Page<Actor> findActorsByFirstName(String firstName, Pageable pageable);

}
