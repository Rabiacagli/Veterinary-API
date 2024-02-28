package dev.patika.Veterinary.dto.request.appointment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @NotNull(message = "ID no boş veya null olamaz.")
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId; // hayvan id'sine göre
    private Long doctorId; // doktor id yle guncelle
}
