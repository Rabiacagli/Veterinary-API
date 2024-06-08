package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.ICustomerRepo;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private final ICustomerRepo customerRepo;
    private final IModelMapperService modelMapper;

    public CustomerManager(ICustomerRepo customerRepo, IModelMapperService modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerResponse save(CustomerSaveRequest customerSave)
    {
        if (existsByNameAndPhoneAndMailAndAddressAndCity(
                        customerSave.getName(),
                        customerSave.getPhone(),
                        customerSave.getMail(),
                        customerSave.getAddress(),
                        customerSave.getCity())
        ) {
            throw new ConflictException("Customer Already Exist");
        }
        Customer saveCustomer = modelMapper.forRequest().map(customerSave, Customer.class);
        this.customerRepo.save(saveCustomer);
        return this.modelMapper.forResponse().map(saveCustomer, CustomerResponse.class);
    }

    @Override
    public Customer get(Long id) {
        return this.customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }
    @Override
    public CustomerResponse getCustomerResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), CustomerResponse.class);
    }
    @Override
    public Page<CustomerResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable).
                map(customer -> this.modelMapper.forResponse().
                        map(customer, CustomerResponse.class));
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest customerUpdateRequest) {
        if (existsByNameAndPhoneAndMailAndAddressAndCity(
                customerUpdateRequest.getName(),
                customerUpdateRequest.getPhone(),
                customerUpdateRequest.getMail(),
                customerUpdateRequest.getAddress(),
                customerUpdateRequest.getCity())
        ) {
            throw new ConflictException("Customer Already Exist");
        }

        Customer customer = this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class);
        this.get(customer.getCustomerId());
        this.customerRepo.save(customer);
        return this.modelMapper.forResponse().map(customer, CustomerResponse.class);
    }
    @Override
    public boolean delete(Long id) {
        this.customerRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<AnimalResponse> getAnimals(Long id) {
        return this.get(id).getAnimalList().stream()
                .map(animal -> this.modelMapper.forResponse()
                        .map(animal, AnimalResponse.class)).toList();
    }

    @Override
    public List<CustomerResponse> getByName(String name) {
        return customerRepo.findByName(name).stream()
                .map(customer -> this.modelMapper.forResponse()
                        .map(customer, CustomerResponse.class)).toList();
    }

    @Override
    public Boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city)
    {
        return customerRepo.existsByNameAndPhoneAndMailAndAddressAndCity
                (name, phone, mail, address, city);
    }
}
