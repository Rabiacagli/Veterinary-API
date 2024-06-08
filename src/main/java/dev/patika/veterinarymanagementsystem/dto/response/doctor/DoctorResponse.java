package dev.patika.veterinarymanagementsystem.dto.response.doctor;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private Long doctorId;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}

