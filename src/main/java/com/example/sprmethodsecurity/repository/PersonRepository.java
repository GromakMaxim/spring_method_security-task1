package com.example.sprmethodsecurity.repository;

import com.example.sprmethodsecurity.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.city=:city")
    List<Person> customizedSearchByCity(@Param("city") String cityOfLiving);

    @Query("select p from Person p where p.age < :age order by :age desc ")
    List<Person> customizedSearchByAge(@Param("age") int age);

    @Query("select p from Person p where p.name = :name and p.surname = :surname")
    List<Optional<Person>> customizedSearchByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    List<Person> findByName(String name);
}
