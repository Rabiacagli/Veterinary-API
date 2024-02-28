package dev.patika.Veterinary.dto.request.animal;

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

    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private Long id;
    @NotNull(message = "Hayvan ismi boş veya null olamaz")
    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Long customerId;

}
