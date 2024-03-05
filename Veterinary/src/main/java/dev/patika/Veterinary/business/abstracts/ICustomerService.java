package dev.patika.Veterinary.business.abstracts;
import dev.patika.Veterinary.dto.request.customer.CustomerSaveRequest;
import dev.patika.Veterinary.dto.request.customer.CustomerUpdateRequest;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.dto.response.customer.CustomerResponse;
import dev.patika.Veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ICustomerService {
    CustomerResponse save(CustomerSaveRequest customer);

    Customer get(Long id);

    CustomerResponse getCustomerResponse(Long id);

    Page<CustomerResponse> cursor(int page, int pageSize);

    CustomerResponse update(CustomerUpdateRequest customer);

    boolean delete(Long id);

    List<AnimalResponse> filterAnimalsByCustomer(Long customerId);

    List<CustomerResponse> findByName(String name);

    Boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city);
}
