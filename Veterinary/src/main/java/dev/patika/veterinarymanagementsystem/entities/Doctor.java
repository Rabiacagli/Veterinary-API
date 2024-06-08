package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "doctor_name", nullable = false)
    private String name;

    @Column(name = "doctor_phone", nullable = false)
    private String phone;

    @Column(name = "doctor_mail")
    private String mail;

    @Column(name = "doctor_address", nullable = false)
    private String address;

    @Column(name = "doctor_city", nullable = false)
    private String city;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentList;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvailableDate> availableDateList;
}

