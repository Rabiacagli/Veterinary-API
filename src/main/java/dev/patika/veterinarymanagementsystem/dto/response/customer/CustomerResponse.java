package dev.patika.veterinarymanagementsystem.dto.response.customer;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
