package dev.patika.Veterinary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "animal_name",nullable = false)
    private String name;

    @Column(name = "animal_species",nullable = false)
    private String species;

    @Column(name = "animal_breed")
    private String breed;

    @Column(name = "animal_gender")
    private String gender;

    @Column(name = "animal_colour")
    private String colour;

    @Column(name = "animal_dateOfBirth")
    private LocalDate dateOfBirth;

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "animal_customer_id" ,referencedColumnName = "customer_id")
    private Customer customer;
 /*
    @OneToMany(mappedBy = "animal")
    private List<Vaccine> vaccineList;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointmentList;*/

}
