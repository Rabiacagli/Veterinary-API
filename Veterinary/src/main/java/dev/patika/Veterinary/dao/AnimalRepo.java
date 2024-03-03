package dev.patika.Veterinary.dao;

import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    List<Animal> findByName (String name);


}
