package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IAvailableDateRepo extends JpaRepository<AvailableDate, Long> {
    Boolean existsByAvailableDateAndDoctor_DoctorId
            (LocalDate availableDate,
             Long doctorId);
}
