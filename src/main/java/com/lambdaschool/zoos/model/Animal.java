package com.lambdaschool.zoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animal")

public class Animal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long animalid;

    private String animalType;

    @ManyToMany
    @JoinTable(name = "zooanimals", joinColumns = {@JoinColumn(name = "animalid")}, inverseJoinColumns =
            {@JoinColumn(name = "zooid")})
    @JsonIgnoreProperties("animals")
    private Set<Zoo> zoos = new HashSet<>();

    public Animal()
    {
        // default constructor
    }

    public long getAnimalid()
    {
        return animalid;
    }

    public void setAnimalid(long animalid)
    {
        this.animalid = animalid;
    }

    public String getAnimalType()
    {
        return animalType;
    }

    public void setAnimalType(String animalType)
    {
        this.animalType = animalType;
    }

    public Set<Zoo> getZoos()
    {
        return zoos;
    }

    public void setZoos(Set<Zoo> zoos)
    {
        this.zoos = zoos;
    }
}
