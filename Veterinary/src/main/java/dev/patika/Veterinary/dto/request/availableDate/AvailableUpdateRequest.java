package dev.patika.Veterinary.dto.request.availableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableUpdateRequest {
    @NotNull(message = "ID number cannot be empty or null!")
    private Long id;

    private LocalDate availableDate;

    private Long doctorId;
}
