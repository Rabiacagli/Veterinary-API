package dev.patika.veterinarymanagementsystem.dto.response.report;

import dev.patika.veterinarymanagementsystem.entities.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private Long id;
    private String diagnosis;
    private double price;
    private Appointment appointment;
}
