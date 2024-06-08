package dev.patika.veterinarymanagementsystem.dto.response.appointment;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
}
