package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String diagnosis;

    private double price;

    @OneToOne
    @JoinColumn (name = "appointment_id")
    private Appointment appointment;

    @JsonManagedReference
    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Vaccine> vaccineList;


}
