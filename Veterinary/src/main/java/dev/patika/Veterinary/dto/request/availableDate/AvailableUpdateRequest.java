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
    @NotNull(message = "ID no boş veya null olamaz.")
    private Long id;
    private LocalDate availableDate;
}
