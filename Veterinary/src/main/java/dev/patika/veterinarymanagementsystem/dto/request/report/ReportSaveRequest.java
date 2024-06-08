package dev.patika.veterinarymanagementsystem.dto.request.report;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSaveRequest {

    @NotNull(message = "Boş veya null olamaz")
    private String diagnosis;
    @NotNull(message = "Boş veya null olamaz")
    private double price;
    @NotNull(message = "Boş veya null olamaz")
    private Long appointmentId;
}

