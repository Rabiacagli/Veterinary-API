package dev.patika.Veterinary.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    @NotNull(message = "ID no cannot be empty or null")
    private Long id;

    @NotNull(message = "Appointment date cannot be empty or null")
    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
}
