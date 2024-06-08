package dev.patika.veterinarymanagementsystem.dto.request.appointment;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @Positive(message = "Id Pozitif bir sayı olmalıdır")
    private Long appointmentId;
    @NotNull(message = "Randevu Günü Boş Olamaz")
    private LocalDateTime appointmentDate;
    @NotNull(message = "Hayvan Id Boş Olamaz")
    private Long animalId;
    @NotNull(message = "Doktor Id Boş Olamaz")
    private Long doctorId;
}
