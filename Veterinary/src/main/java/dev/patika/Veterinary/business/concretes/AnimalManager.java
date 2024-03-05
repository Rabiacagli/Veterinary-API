package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.request.animal.AnimalUpdateRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import static dev.patika.Veterinary.core.utilies.Msg.NOT_FOUND;

@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;


    public AnimalManager(AnimalRepo animalRepo, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalRepo = animalRepo;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @Override
    public AnimalResponse save(AnimalSaveRequest animalSave) {         // Değerlendirme 11
        if(this.existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(
                animalSave.getName(),
                animalSave.getCustomerId(),
                animalSave.getSpecies(),
                animalSave.getBreed(),
                animalSave.getDateOfBirth())){
            throw new ConflictException("Animal already exists!");     // Değerlendirme 25
        }
        Customer customer = this.customerService.get(animalSave.getCustomerId());
        animalSave.setCustomerId(null);
        Animal animal = this.modelMapper.forRequest().map(animalSave,Animal.class);
        animal.setCustomer(customer);
        this.animalRepo.save(animal);
        return this.modelMapper.forResponse().map(animal,AnimalResponse.class);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Override
    public AnimalResponse getAnimalResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id),AnimalResponse.class);
    }

    @Override
    public Page<AnimalResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Animal> animalPage =  this.animalRepo.findAll(pageable);
        return animalPage.map(animal -> this.modelMapper.forResponse().map(animal,AnimalResponse.class));

       // return this.animalRepo.findAll(PageRequest.of(page,pageSize)).map(animal -> this.modelMapper.forResponse().map(animal,AnimalResponse.class));
    }

    @Override
    public AnimalResponse update(AnimalUpdateRequest animalUpdate) {
        if(this.existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(
                animalUpdate.getName(),
                animalUpdate.getCustomerId(),
                animalUpdate.getSpecies(),
                animalUpdate.getBreed(),
                animalUpdate.getDateOfBirth())){
            throw new ConflictException("Animal already exists!");          // Değerlendirme 25
        }
        Animal animal = this.modelMapper.forRequest().map(animalUpdate,Animal.class);
        Customer customer = customerService.get(animalUpdate.getCustomerId());
        animal.setCustomer(customer);
        this.get(animal.getId());
        this.animalRepo.save(animal);
        return this.modelMapper.forResponse().map(animal,AnimalResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public List<VaccineResponse> filterVaccinesByAnimal(Long animalId) {     // Değerlendirme 20
        return this.get(animalId).getVaccineList().stream()
                .map(vaccine -> this.modelMapper.forResponse()
                        .map(vaccine,VaccineResponse.class)).toList();
    }

    @Override
    public List<AnimalResponse> findByName(String name) {          // Değerlendirme 16
        return this.animalRepo.findByName(name).stream()
                .map(animal -> this.modelMapper.forResponse()
                        .map(animal, AnimalResponse.class)).toList();
    }

    @Override
    public boolean existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(
            String name, Long customerId, String species, String breed, LocalDate dateOfBirth) {
        return this.animalRepo.existsByNameAndCustomer_IdAndSpeciesAndBreedAndDateOfBirth(
                name, customerId, species, breed, dateOfBirth);
    }
}


