package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);

    Vaccine get(Long id);

    Page<Vaccine> cursor(int page, int pageSize);

    Vaccine update(Vaccine vaccine);

    boolean delete(Long id);
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate protectionStartDate, LocalDate protectionFinishDate);
}
