package dev.patika.Veterinary.dto.request.animal;

import dev.patika.Veterinary.entities.Animal;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {

    @NotNull(message = "Hayvan ismi boş veya null olamaz")
    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;

    private Long customerId; // hayvan sahibi kaydetmek için id tut



}
