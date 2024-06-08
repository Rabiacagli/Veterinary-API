package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IVaccineRepo extends JpaRepository<Vaccine, Long> {


    List<Vaccine> findByProtectionFinishDateBetween
            (LocalDate startDate,
             LocalDate endDate);

    Boolean existsByVaccineCodeAndProtectionFinishDateAfter
            (String vaccineCode,
             LocalDate protectionStartDate);

}
