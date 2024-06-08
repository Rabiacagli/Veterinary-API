package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IAnimalRepo extends JpaRepository<Animal, Long> {

    List<Animal> findByName(String name);


    Boolean existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth
            (String name,
             Long customerId,
             String species,
             String breed,
             LocalDate dateOfBirth);

}
