package dev.patika.Veterinary.entities;

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

@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_mail", unique = true)
    private String mail;

    @Column(name = "customer_address")
    private String address;

    @Column(name = "customer_city")
    private String city;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Animal> animalList;
}
