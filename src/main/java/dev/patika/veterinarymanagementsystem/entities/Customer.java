package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "customer_name", nullable = false)
    private String name;
    @Column(name = "customer_mail")
    private String mail;
    @Column(name = "customer_address")
    private String address;
    @Column(name = "customer_city")
    private String city;
    @Column(name = "customer_phone", nullable = false)
    private String phone;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animalList;
}
