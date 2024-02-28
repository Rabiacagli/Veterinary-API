package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {

    Animal save(Animal animal);

    Animal get(Long id);

    Page<Animal> cursor(int page, int pageSize);

    Animal update(Animal animal);

    boolean delete(Long id);


    List<Vaccine> filterVaccinesByAnimal(Long animalId); // hayvanın aşılarını getirir




}
