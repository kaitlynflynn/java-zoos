package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Animalrepository extends JpaRepository<Animal, Long>
{
    Animal findByAnimalTypeEqualsIgnoreCase(String name);
}
