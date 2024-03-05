package dev.patika.Veterinary.business.abstracts;
import dev.patika.Veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.Veterinary.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Vaccine;
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
    List<VaccineResponse> findByProtectionFinishDateBetween(
            LocalDate protectionStartDate, LocalDate protectionFinishDate);

    boolean existsByCodeAndProtectionFinishDateAfter(String code, LocalDate protectionStartDate);
}
