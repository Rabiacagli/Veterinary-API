package dev.patika.Veterinary.dto.response.doctor;

import dev.patika.Veterinary.entities.Appointment;
import dev.patika.Veterinary.entities.AvailableDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
   /* private List<Appointment> appointmentList; // randevuları tutmak için
    private List<AvailableDate> availableDateList; // müsait gunleri tutmak icin*/
}
