package dev.patika.veterinarymanagementsystem.dto.request.availableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {
    @NotNull(message = "Müsait Günler Boş veya Null olamaz")
    private LocalDate availableDate;
    @NotNull(message = "Doktor Id Boş veya Null olamaz")
    private Long doctorId;
}
