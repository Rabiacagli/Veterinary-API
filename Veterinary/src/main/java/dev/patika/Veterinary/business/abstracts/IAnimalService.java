package dev.patika.Veterinary.business.abstracts;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.request.animal.AnimalUpdateRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Animal;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {

    AnimalResponse save(AnimalSaveRequest animal);

    Animal get(Long id);

    AnimalResponse getAnimalResponse(Long id);

    Page<AnimalResponse> cursor(int page, int pageSize);

    AnimalResponse update(AnimalUpdateRequest animal);

    boolean delete(Long id);

    List<VaccineResponse> filterVaccinesByAnimal(Long animalId);

    List<AnimalResponse> findByName (String name);

    boolean existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(
            String name, Long customerId, String species, String breed, LocalDate dateOfBirth);
}
