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

    @NotNull(message = "Randevu tarihi boş veya null olamaz.")
    private LocalDateTime appointmentDate;
    private Long animalId; // hayvan id'sine göre
    private Long doctorId; // doktor id yle gore kaydet
}
