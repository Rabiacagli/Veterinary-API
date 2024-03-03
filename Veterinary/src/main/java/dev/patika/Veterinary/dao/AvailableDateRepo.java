package dev.patika.Veterinary.dao;

import dev.patika.Veterinary.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    boolean existsByAvailableDateAndDoctor_Id(LocalDate availableDate, Long doctorId);
}
