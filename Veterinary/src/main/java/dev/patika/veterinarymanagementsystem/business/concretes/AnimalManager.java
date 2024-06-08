package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IAnimalRepo;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    private final IAnimalRepo animalRepo;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;

    public AnimalManager(IAnimalRepo animalRepo, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalRepo = animalRepo;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnimalResponse save(AnimalSaveRequest animalSave) {
        if (existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth(animalSave.getName(), animalSave.getCustomerId(), animalSave.getSpecies(), animalSave.getBreed(), animalSave.getDateOfBirth())) {
            throw new ConflictException("Animal Already Exist");
        }

        Customer customer = customerService.get(animalSave.getCustomerId());
        animalSave.setCustomerId(null);
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSave, Animal.class);
        saveAnimal.setCustomer(customer);
        this.animalRepo.save(saveAnimal);
        return this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class);
    }

    @Override
    public AnimalResponse getAnimalResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), AnimalResponse.class);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<AnimalResponse> cursor(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Animal> animalPage = this.animalRepo.findAll(pageable);

        return animalPage.map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));
    }

    @Override
    public AnimalResponse update(AnimalUpdateRequest animalUpdate) {

        if ((animalUpdate.getAnimalId() == 0) && existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth(animalUpdate.getName(), animalUpdate.getCustomerId(), animalUpdate.getSpecies(), animalUpdate.getBreed(), animalUpdate.getDateOfBirth())) {
            throw new ConflictException("Animal Already Exist");
        }

        Animal animal = this.modelMapper.forRequest().map(animalUpdate, Animal.class);
        Customer customer = customerService.get(animalUpdate.getCustomerId());
        animal.setCustomer(customer);
        this.get(animal.getAnimalId());
        this.animalRepo.save(animal);
        return this.modelMapper.forResponse().map(animal, AnimalResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Animal animal = this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.animalRepo.delete(animal);
        return true;

    }

    @Override
    public List<VaccineResponse> getVaccines(Long id) {
        return this.get(id).getVaccineList().stream().map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class)).toList();
    }

    @Override
    public List<AnimalResponse> getByName(String name) {
        return this.animalRepo.findByName(name).stream().map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class)).toList();
    }

    @Override
    public Boolean existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth(String name, Long customerId, String species, String breed, LocalDate dateOfBirth) {

        return this.animalRepo.existsByNameAndCustomer_CustomerIdAndSpeciesAndBreedAndDateOfBirth(name, customerId, species, breed, dateOfBirth);
    }
}
