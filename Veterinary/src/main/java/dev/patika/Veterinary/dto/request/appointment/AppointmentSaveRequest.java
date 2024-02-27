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
}
