package dev.patika.Veterinary.business.concretes;

import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dao.CustomerRepo;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;
    private final AnimalRepo animalRepo;

    public CustomerManager(CustomerRepo customerRepo, AnimalManager animalManager, AnimalRepo animalRepo) {
        this.customerRepo = customerRepo;
        this.animalRepo = animalRepo;

    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(Long id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable);
    }

    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId());
        return this.customerRepo.save(customer);
    }

    @Override
    public boolean delete(Long id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public List<Animal> filterAnimalsByCustomer(Long customerId) {
        return this.get(customerId).getAnimalList();
    }

}
