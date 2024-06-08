package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineProtectionDateAndAnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    VaccineResponse save(VaccineSaveRequest vaccine);

    Vaccine get(Long id);
    VaccineResponse getVaccineResponse(Long id);

    Page<VaccineResponse> cursor(int page, int pageSize);

    VaccineResponse update(VaccineUpdateRequest vaccine);

    boolean delete(Long id);

    List<VaccineProtectionDateAndAnimalResponse> findByProtectionFinishDateBetween
            (LocalDate startDate,
             LocalDate endDate);

    Boolean existsByVaccineCodeAndProtectionFinishDateAfter
            (String vaccineCode,
             LocalDate protectionStartDate);


}

