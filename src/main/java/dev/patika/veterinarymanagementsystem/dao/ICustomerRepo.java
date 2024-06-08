package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String name);

    Boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city);
}
