package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vaccines")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long vaccineId;

    @Column(name = "vaccine_name", nullable = false)
    private String name;

    @Column(name = "vaccine_code", nullable = false)
    private String vaccineCode;

    @Column(name = "protection_start_date", nullable = false)
    private LocalDate protectionStartDate;

    @Column(name = "protection_finish_date", nullable = false)
    private LocalDate protectionFinishDate;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "vaccine_animal_id", referencedColumnName = "animal_id")
    private Animal animal;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_report_id", referencedColumnName = "id")
    private Report report;

}
