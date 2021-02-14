package com.lambdaschool.zoos.controller;

import com.lambdaschool.zoos.model.Animal;
import com.lambdaschool.zoos.repository.Animalrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/animals", produces = MediaType.APPLICATION_JSON_VALUE)

public class Animalcontroller
{
    @Autowired
    Animalrepository animalrepo;

    @GetMapping(value = "/animals")
    public List<Animal> listAllZoos()
    {
        return animalrepo.findAll();
    }

    @GetMapping(value = "/{name}")
    public Animal getAnimalByName(@PathVariable String name)
    {
        return animalrepo.findByAnimalTypeEqualsIgnoreCase(name);
    }
}
