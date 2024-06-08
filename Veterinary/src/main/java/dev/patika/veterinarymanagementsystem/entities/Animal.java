package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long animalId;

    @Column(name = "animal_name", nullable = false)
    private String name;

    @Column(name = "animal_species", nullable = false)
    private String species;

    @Column(name = "animal_breed", nullable = false)
    private String breed;

    @Column(name = "animal_gender", nullable = false)
    private String gender;

    @Column(name = "animal_colour", nullable = false)
    private String colour;

    @Temporal(TemporalType.DATE)
    @Column(name = "animal_birthdate")
    private LocalDate dateOfBirth;

    @JsonManagedReference
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaccine> vaccineList;

    @JsonManagedReference
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentList;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    private Customer customer;

}
