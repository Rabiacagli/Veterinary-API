package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IAnimalService {

    AnimalResponse save(AnimalSaveRequest animal);

    Animal get(Long id);

    Page<Animal> cursor(int page, int pageSize);

    Animal update(Animal animal);

    boolean delete(Long id);


    List<Vaccine> filterVaccinesByAnimal(Long animalId); // hayvanın aşılarını getirir


    List<Animal> findByName (String name);








}
