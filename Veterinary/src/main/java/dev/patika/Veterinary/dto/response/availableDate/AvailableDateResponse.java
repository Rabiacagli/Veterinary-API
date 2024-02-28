package dev.patika.Veterinary.dto.response.availableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AvailableDateResponse {
    private Long id;
    private LocalDate availableDate;
    private Long doctorId; // doktor id yle bırlıkte goster
}
