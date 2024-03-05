package dev.patika.Veterinary.dao;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate protectionStartDate, LocalDate protectionFinishDate);

    boolean existsByCodeAndProtectionFinishDateAfter(String code, LocalDate protectionStartDate);
}