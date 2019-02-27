package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface Zoorepository extends JpaRepository<Zoo, Long>
{
    Zoo findByZooNameEqualsIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid", nativeQuery = true)
    void deleteAnimalsFromZooAnimalsByZooId(long zooid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE animalid = :animalid", nativeQuery = true)
    void deleteAnimalByIdFromZooAnimals(long animalid);
}
