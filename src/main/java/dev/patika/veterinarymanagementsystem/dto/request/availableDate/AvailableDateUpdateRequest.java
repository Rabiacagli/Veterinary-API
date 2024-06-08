package dev.patika.veterinarymanagementsystem.dto.request.availableDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {
    @Positive(message = "Id pozitif bir sayı olmalıdır")
    private Long availableDateId;
    @NotNull(message = "Müsait Günler Boş veya Null olamaz")
    private LocalDate availableDate;
    @NotNull(message = "Doktor Id Boş veya Null olamaz")
    private Long doctorId;
}
