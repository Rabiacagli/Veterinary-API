package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {
    AnimalResponse save(AnimalSaveRequest animal);

    AnimalResponse getAnimalResponse(Long id);
    Animal get(Long id);

    Page<AnimalResponse> cursor(int page, int pageSize);

    AnimalResponse update(AnimalUpdateRequest animal);

    boolean delete(Long id);

    List<VaccineResponse> getVaccines(Long id);

    List<AnimalResponse> getByName(String name);

    Boolean existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth
            (String name,
             Long customerId,
             String species,
             String breed,
             LocalDate dateOfBirth);


}

