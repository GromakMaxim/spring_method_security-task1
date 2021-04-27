package com.example.sprmethodsecurity.service;

import com.example.sprmethodsecurity.model.Person;
import com.example.sprmethodsecurity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private PersonRepository personRepository;

    public List customizedSearchByCity(String city) {
        return personRepository.customizedSearchByCity(city);
    }

    public List customizedSearchByAge(int age) {
        return personRepository.customizedSearchByAge(age);
    }

    public List<Optional<Person>> customizedSearchByNameAndSurname(String name, String surname) {
        return personRepository.customizedSearchByNameAndSurname(name, surname);
    }

    public List<Person> getPersonsByName(String name) {
        return personRepository.findByName(name);
    }
}
