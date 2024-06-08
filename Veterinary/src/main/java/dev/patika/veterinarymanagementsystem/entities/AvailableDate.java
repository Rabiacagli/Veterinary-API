package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "available_dates")
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private Long availableDateId;

    @Column(name = "available_date")
    private LocalDate availableDate;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "availabledate_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;
}
