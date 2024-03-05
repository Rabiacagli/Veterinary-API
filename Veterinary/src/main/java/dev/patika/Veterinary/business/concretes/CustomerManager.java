package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.CustomerRepo;
import dev.patika.Veterinary.dto.request.customer.CustomerSaveRequest;
import dev.patika.Veterinary.dto.request.customer.CustomerUpdateRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.dto.response.customer.CustomerResponse;
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
    public CustomerManager(CustomerRepo customerRepo, IModelMapperService modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerResponse save(CustomerSaveRequest customerSave) {         // Değerlendirme 10

        if (this.existsByNameAndPhoneAndMailAndAddressAndCity(customerSave.getName(),
                customerSave.getPhone(),
                customerSave.getMail(),
                customerSave.getAddress(),
                customerSave.getCity())) {
            throw new ConflictException("Customer already exists, please check informations and try again");  // Değerlendirme 25
        }
        Customer customer = this.modelMapper.forRequest().map(customerSave,Customer.class);
        this.customerRepo.save(customer);
        return this.modelMapper.forResponse().map(customer, CustomerResponse.class);
    }

    @Override
    public Customer get(Long id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public CustomerResponse getCustomerResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), CustomerResponse.class);
    }

    @Override
    public Page<CustomerResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Customer> customerPage = this.customerRepo.findAll(pageable);
        return customerPage.map(customer -> this.modelMapper.forResponse().map(customer,CustomerResponse.class));
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest customerUpdate) {
        if (this.existsByNameAndPhoneAndMailAndAddressAndCity(customerUpdate.getName(),
                customerUpdate.getPhone(),
                customerUpdate.getMail(),
                customerUpdate.getAddress(),
                customerUpdate.getCity())) {
            throw new ConflictException("Customer already exists, please check informations and try again"); // Değerlendirme 25
        }
        Customer customer = this.modelMapper.forRequest().map(customerUpdate, Customer.class);
        this.get(customer.getId());
        this.customerRepo.save(customer);
        return this.modelMapper.forResponse().map(customer,CustomerResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public List<AnimalResponse> filterAnimalsByCustomer(Long customerId) {        // Değerlendirme 18
        return this.get(customerId).getAnimalList().stream()
                .map(animal -> this.modelMapper.forResponse()
                        .map(animal,AnimalResponse.class)).toList();
    }

    @Override
    public List<CustomerResponse> findByName(String name) {        // Değerlendirme 17
        return this.customerRepo.findByName(name).stream()
                .map(customer -> this.modelMapper.forResponse()
                        .map(customer,CustomerResponse.class)).toList();
    }

    @Override
    public Boolean existsByNameAndPhoneAndMailAndAddressAndCity(
            String name, String phone, String mail, String address, String city) {
        return this.customerRepo.existsByNameAndPhoneAndMailAndAddressAndCity
                (name, phone, mail, address, city);
    }
}
