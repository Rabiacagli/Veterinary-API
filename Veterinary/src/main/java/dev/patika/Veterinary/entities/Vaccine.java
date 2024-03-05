package dev.patika.Veterinary.entities;
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
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "vaccine_name", nullable = false)
    private String name;

    @Column(name = "vaccine_code")
    private String code;

    @Column(name = "vac_protection_start_date")
    private LocalDate protectionStartDate;

    @Column(name = "vac_protection_finish_date")
    private LocalDate protectionFinishDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "vaccine_animal_id" ,referencedColumnName = "animal_id")   // Değerlendirme 9
    private Animal animal;
}
