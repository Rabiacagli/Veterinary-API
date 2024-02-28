package dev.patika.Veterinary.dto.response.animal;

import dev.patika.Veterinary.entities.Appointment;
import dev.patika.Veterinary.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {

    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Long customerId;  // hayvan sahibi için id

}
