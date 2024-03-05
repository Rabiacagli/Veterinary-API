package dev.patika.Veterinary.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "appointment_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)          // Değerlendirme 9
    @JoinColumn(name  = "appointment_animal_id" ,referencedColumnName = "animal_id")
    private Animal animal;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)         // Değerlendirme 9
    @JoinColumn(name  = "appointment_doctor_id" ,referencedColumnName = "doctor_id")
    private Doctor doctor;

}
