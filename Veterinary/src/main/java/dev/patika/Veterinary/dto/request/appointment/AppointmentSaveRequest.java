package dev.patika.Veterinary.dto.request.appointment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {

    @NotNull(message = "Appointment date cannot be empty or null")
    private LocalDateTime appointmentDate;

    private Long animalId;

    private Long doctorId;
}
