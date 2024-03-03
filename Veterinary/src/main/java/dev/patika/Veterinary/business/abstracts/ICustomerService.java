package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.dto.request.customer.CustomerSaveRequest;
import dev.patika.Veterinary.dto.response.customer.CustomerResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    CustomerResponse save(CustomerSaveRequest customer);

    Customer get(Long id);

    Page<Customer> cursor(int page, int pageSize);

    Customer update(Customer customer);

    boolean delete(Long id);

    List<Animal> filterAnimalsByCustomer(Long customerId);

    List<Customer> findByName(String name);
}
