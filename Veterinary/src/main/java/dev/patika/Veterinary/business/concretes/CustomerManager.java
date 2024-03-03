package dev.patika.Veterinary.business.concretes;

import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dao.CustomerRepo;
import dev.patika.Veterinary.dto.request.customer.CustomerSaveRequest;
import dev.patika.Veterinary.dto.response.customer.CustomerResponse;
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
    private final IModelMapperService modelMapper;
    public CustomerManager(CustomerRepo customerRepo, AnimalRepo animalRepo, IModelMapperService modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerResponse save(CustomerSaveRequest customerSave) {
        Customer customer = this.modelMapper.forRequest().map(customerSave,Customer.class);  //Gelen isteği(requesti) veri tabanında tutuğumuz entitie ye mapliyoruz.(Çeviriyoruz :)) )
        this.customerRepo.save(customer);
        return this.modelMapper.forResponse().map(customer, CustomerResponse.class);
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

    @Override
    public List<Customer> findByName(String name) {
        return this.customerRepo.findByName(name);
    }

}
