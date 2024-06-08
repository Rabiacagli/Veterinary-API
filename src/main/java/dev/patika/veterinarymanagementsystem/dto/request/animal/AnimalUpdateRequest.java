package dev.patika.veterinarymanagementsystem.dto.request.animal;

import dev.patika.veterinarymanagementsystem.entities.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    @Positive(message = "Id değeri pozitif sayı olmak zorundadır")
    private Long animalId;
    @NotNull(message = "İsim boş veya null olamaz")
    private String name;
    @NotNull(message = "Tür boş veya null olamaz")
    private String species;
    @NotNull(message = "Irk boş veya null olamaz")
    private String breed;
    @NotNull(message = "Cinsiyet boş veya null olamaz")
    private String gender;
    @NotNull(message = "Renk boş veya null olamaz")
    private String colour;
    @NotNull(message = "Doğum tarihi boş veya null olamaz")
    private LocalDate dateOfBirth;
    @NotNull(message = "Sahip id boş veya null olamaz")
    private Long customerId;
}
