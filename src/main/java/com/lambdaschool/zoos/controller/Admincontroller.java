package com.lambdaschool.zoos.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lambdaschool.zoos.model.Animal;
import com.lambdaschool.zoos.model.Telephone;
import com.lambdaschool.zoos.model.Zoo;
import com.lambdaschool.zoos.repository.Animalrepository;
import com.lambdaschool.zoos.repository.Telephonerepository;
import com.lambdaschool.zoos.repository.Zoorepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/admin/", produces = MediaType.APPLICATION_JSON_VALUE)

public class Admincontroller
{
    @Autowired
    Zoorepository zoorepo;

    @Autowired
    Telephonerepository telephonerepo;

    @Autowired
    Animalrepository animalrepo;

    @PutMapping(value = "/zoos/{id}")
    public Zoo updateZooById(@PathVariable long id, @RequestBody Zoo zoo) throws URISyntaxException
    {
        Optional<Zoo> foundZoo = zoorepo.findById(id);
        if(foundZoo.isPresent())
        {
            zoo.setZooid(id);
            zoorepo.save(zoo);
            return zoo;
        } else
        {
            return null;
        }
    }

    @PutMapping(value = "/telephones/{id}")
    public Telephone updateTelephoneById(@PathVariable long id, @RequestBody Telephone telephone) throws URISyntaxException
    {
        Optional<Telephone> foundTelephone = telephonerepo.findById(id);
        if (foundTelephone.isPresent())
        {
            telephone.setPhoneid(id);
            telephonerepo.save(telephone);
            return telephone;
        } else
        {
            return null;
        }
    }

    @PutMapping(value = "/animals/{id}")
    public Animal updatedAnimalById(@PathVariable long id, @RequestBody Animal animal) throws URISyntaxException
    {
        Optional<Animal> foundAnimal = animalrepo.findById(id);
        if (foundAnimal.isPresent())
        {
            animal.setAnimalid(id);
            animalrepo.save(animal);
            return animal;
        } else
        {
            return null;
        }
    }

    @PostMapping(value = "/zoos")
    public Zoo addZoo(@RequestBody Zoo zoo) throws URISyntaxException
    {
        return zoorepo.save(zoo);
    }

    @PostMapping(value = "/phones")
    public Telephone addTelephone(@RequestBody Telephone telephone) throws URISyntaxException
    {
        return telephonerepo.save(telephone);
    }

    @PostMapping(value = "/animals")
    public Animal addAnimal(@RequestBody Animal animal) throws URISyntaxException
    {
        return animalrepo.save(animal);
    }

    @PostMapping(value = "/zoos/animals")
    public ObjectNode addAnimalToZoo(@RequestBody ObjectNode zooanimal)
    {
        long zooid = zooanimal.get("zooid").asLong();
        long animalid = zooanimal.get("animalid").asLong();

        Optional<Zoo> foundZoo = zoorepo.findById(zooid);
        Optional<Animal> foundAnimal = animalrepo.findById(animalid);

        if (foundZoo.isPresent() && foundAnimal.isPresent())
        {
            Animal animal = foundAnimal.get();
            animal.getZoos().add(foundZoo.get());
            animalrepo.save(animal);
            return zooanimal;
        } else
        {
            return null;
        }
    }

    @DeleteMapping(value = "/zoos/{id}")
    public String deleteZooById(@PathVariable long id)
    {
        Optional<Zoo> foundZoo = zoorepo.findById(id);
        if (foundZoo.isPresent())
        {
            for (Telephone telephone : foundZoo.get().getTelephones())
            {
                telephonerepo.delete(telephone);
            }
            zoorepo.deleteAnimalsFromZooAnimalsByZooId(id);
            zoorepo.deleteById(id);
            return "{" + "\"Success\":" + "\"Zoo deleted.\"" + "}";
        } else
        {
            return "{" + "\"Error\":" + "\"Zoo cannot be deleted.\"" + "}";
        }
    }

    @DeleteMapping(value = "/phones/{id}")
    public String deleteTelephoneById(@PathVariable long id)
    {
        Optional<Telephone> foundTelephone = telephonerepo.findById(id);
        if (foundTelephone.isPresent())
        {
            telephonerepo.delete(foundTelephone.get());
            return "{" + "\"Success\":" + "\"Telephone deleted.\"" + "}";
        } else
        {
            return "{" + "\"Error\":" + "\"Telephone cannot be deleted.\"" + "}";
        }
    }

    @DeleteMapping(value = "/animals/{id}")
    public String deleteAnimalById(@PathVariable long id)
    {
        Optional<Animal> foundAnimal = animalrepo.findById(id);
        if (foundAnimal.isPresent())
        {
            animalrepo.deleteById(id);
            return "{" + "\"Success\":" + "\"Animal deleted.\"" + "}";
        } else
        {
            return "{" + "\"Error\":" + "\"Animal cannot be deleted.\"" + "}";
        }
    }

    @DeleteMapping(value = "/zoos/{zooid}/animals/{animalid}")
    public String removeAnimalByIdFromZooById(@PathVariable long zooid, @PathVariable long animalid)
    {
        Optional<Zoo> foundZoo = zoorepo.findById(zooid);
        Optional<Animal> foundAnimal = animalrepo.findById(animalid);

        if (foundZoo.isPresent() && foundAnimal.isPresent())
        {
            Animal animal = foundAnimal.get();
            animal.getZoos().remove(foundZoo.get());
            animalrepo.save(animal);

            return "{" + "\"Success\":" + "\"Animal deleted from Zoo.\"" + "}";
        } else if (!foundZoo.isPresent())
        {
            return "{" + "\"Error\":" + "\"Animal cannot be deleted - Zoo not found.\"" + "}";
        } else
        {
            return "{" + "\"Error\":" + "\"Unknown Error\"" + "}";
        }
    }

    @GetMapping(value = "/phones")
    public List<Telephone> listAllPhones()
    {
        return telephonerepo.findAll();
    }
}
