package dev.patika.veterinarymanagementsystem.dto.response.availableDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private Long availableDateId;
    private LocalDate availableDate;
    private Long doctorId;
}
