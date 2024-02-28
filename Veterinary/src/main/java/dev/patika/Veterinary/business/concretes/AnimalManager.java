package dev.patika.Veterinary.business.concretes;

import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dao.VaccineRepo;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final VaccineRepo vaccineRepo;


    public AnimalManager(AnimalRepo animalRepo, VaccineRepo vaccineRepo) {
        this.animalRepo = animalRepo;
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public Animal save(Animal animal) {
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
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



}
