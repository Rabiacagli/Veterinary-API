package dev.patika.Veterinary.dto.response.customer;

import dev.patika.Veterinary.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;

}
