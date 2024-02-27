package dev.patika.Veterinary.dto.request.doctor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {

    @NotNull(message = "Doktor adı boş veya null olamaz")
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
