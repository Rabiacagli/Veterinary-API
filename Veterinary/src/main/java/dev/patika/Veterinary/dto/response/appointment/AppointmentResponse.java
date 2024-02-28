package dev.patika.Veterinary.dto.response.appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;// animal bilgisi görmek için
    private Long doctorId; // dokr ıd gormek ıcın
}
