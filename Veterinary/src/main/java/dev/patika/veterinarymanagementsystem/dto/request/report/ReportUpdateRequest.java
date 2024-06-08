package dev.patika.veterinarymanagementsystem.dto.request.report;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateRequest {

    @Positive(message = "Id pozitif bir sayı olmalıdır")
    private Long id;
    @NotNull(message = "Boş veya null olamaz")
    private String diagnosis;
    @NotNull(message = "Boş veya null olamaz")
    private double price;
    @NotNull(message = "Boş veya null olamaz")
    private Long appointmentId;
}

