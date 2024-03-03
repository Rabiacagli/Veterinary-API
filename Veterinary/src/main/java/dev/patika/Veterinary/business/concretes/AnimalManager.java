package dev.patika.Veterinary.business.concretes;

import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dao.VaccineRepo;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

import static dev.patika.Veterinary.core.utilies.Msg.NOT_FOUND;

@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final VaccineRepo vaccineRepo;

    private final IModelMapperService modelMapper;

    private final ICustomerService customerService;


    public AnimalManager(AnimalRepo animalRepo, VaccineRepo vaccineRepo, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalRepo = animalRepo;
        this.vaccineRepo = vaccineRepo;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @Override
    public AnimalResponse save(AnimalSaveRequest animalSave) {
        Animal animal = this.modelMapper.forRequest().map(animalSave,Animal.class);
        Customer customer = this.customerService.get(animalSave.getCustomerId()); //customer için
        animal.setCustomer(customer);// customer için
        this.animalRepo.save(animal);
        return this.modelMapper.forResponse().map(animal,AnimalResponse.class);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(Long id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public List<Vaccine> filterVaccinesByAnimal(Long animalId) {
        return this.get(animalId).getVaccineList();
    }

    @Override
    public List<Animal> findByName(String name) {
        return this.animalRepo.findByName(name);
    }


}


