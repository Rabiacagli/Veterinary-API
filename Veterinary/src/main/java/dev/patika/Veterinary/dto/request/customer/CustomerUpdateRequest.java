package dev.patika.Veterinary.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    @NotNull(message = "ID no boş veya null olamaz")
    private Long id;
    @NotNull(message = "Misafir adı boş veya null olamaz")
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
