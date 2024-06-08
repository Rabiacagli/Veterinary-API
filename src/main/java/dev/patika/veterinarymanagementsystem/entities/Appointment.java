package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "appointment_animal_id", referencedColumnName = "animal_id")
    private Animal animal;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "appointment_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @OneToOne (mappedBy = "appointment")
    @JsonIgnore
    private Report report;

}
