package com.example.sprmethodsecurity.controller;


import com.example.sprmethodsecurity.model.Person;
import com.example.sprmethodsecurity.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private Service service;

    //example: http://localhost:8080/hello
    @GetMapping("/hello")
    public String showHelloPage() {
        return "Hello from spring security application! (this page is available for everybody)";
    }


    //example: http://localhost:8080/persons/by-city?city=Moscow
    @GetMapping("/persons/by-city")
    @Secured("READ")
    public List getPersonsByCity(@RequestParam String city) {
        return service.customizedSearchByCity(city);
    }

    //example: http://localhost:8080/persons/by-age?age=3
    @GetMapping("/persons/by-age")
    @RolesAllowed("WRITE")
    public List getPersonsByAge(@RequestParam int age) {
        return service.customizedSearchByAge(age);
    }

    //example: http://localhost:8080/persons/by-name-and-surname?name=Phillipps&surname=Nicholson
    @GetMapping("/persons/by-name-and-surname")
    @PreAuthorize("hasAuthority('WRITE') or hasAuthority('DELETE')")
    public List<Optional<Person>> advancedSearch(@RequestParam String name, @RequestParam String surname) {
        return service.customizedSearchByNameAndSurname(name, surname);
    }

    //example: http://localhost:8080/persons/by-name?name=Maxim
    @GetMapping("/persons/by-name")
    @PreAuthorize("#name == authentication.principal.username")
    public List<Person> getPersonByName(@RequestParam String name) {
        return service.getPersonsByName(name);
    }
}