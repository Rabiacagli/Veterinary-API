package dev.patika.Veterinary.dao;
import dev.patika.Veterinary.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    List<Animal> findByName (String name);

    boolean existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(String name, Long customerId, String species, String breed, LocalDate dateOfBirth);
}
