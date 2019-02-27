package com.lambdaschool.zoos.controller;

import com.lambdaschool.zoos.model.Zoo;
import com.lambdaschool.zoos.repository.Zoorepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/zoos", produces = MediaType.APPLICATION_JSON_VALUE)

public class Zoocontroller
{
    @Autowired
    Zoorepository zoorepo;

    @GetMapping(value = "/zoos")
    public List<Zoo> listAllZoos()
    {
        return zoorepo.findAll();
    }

    @GetMapping(value = "/{name}")
    public Zoo getZooByName(@PathVariable String name)
    {
        return zoorepo.findByZooNameEqualsIgnoreCase(name);
    }
}
