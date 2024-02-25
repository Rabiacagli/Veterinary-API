package dev.patika.Veterinary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "availableDates")
public class AvailableDate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availableDate_id", columnDefinition = "serial")
    private Long id;


    @Column(name = "available_date")
    private LocalDate availableDate;

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "availableDate_doctor_id" ,referencedColumnName = "doctor_id")
    private Doctor doctor;*/

}
