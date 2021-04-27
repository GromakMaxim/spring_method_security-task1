package com.example.sprmethodsecurity;

import com.example.sprmethodsecurity.model.Person;
import com.example.sprmethodsecurity.repository.PersonRepository;
import com.example.sprmethodsecurity.service.PersonsCreatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.stream.IntStream;

@Component
public class AppRunner implements CommandLineRunner, PersonsCreatable {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonsCreatable creatable;

    @Override
    @Transactional
    public void run(String... args) {
        //random persons
        var limit = 1_000;
        IntStream.range(0, limit).forEach(i -> personRepository.save(creatable.createPerson()));

        //persons for access testing
        personRepository.save(Person.builder().name("Maxim").surname("Gromak").city("Khabarovsk").age(28).phoneNumber("11111111").build());
        personRepository.save(Person.builder().name("Olga").surname("Kireeva").city("Khabarovsk").age(28).phoneNumber("22222222").build());
        personRepository.save(Person.builder().name("Admin").surname("Admin").city("Khabarovsk").age(28).phoneNumber("123456788").build());
    }
}
