package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    CustomerResponse save(CustomerSaveRequest customerSave);

    Customer get(Long id);

   CustomerResponse getCustomerResponse(Long id);

    Page<CustomerResponse> cursor(int page, int pageSize);

    CustomerResponse update(CustomerUpdateRequest customerUpdateRequest);

    boolean delete(Long id);

    List<AnimalResponse> getAnimals(Long id);

    List<CustomerResponse> getByName(String name);

    Boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city);
}
