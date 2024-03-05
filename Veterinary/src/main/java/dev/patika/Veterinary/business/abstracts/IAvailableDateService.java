package dev.patika.Veterinary.business.abstracts;
import dev.patika.Veterinary.dto.request.availableDate.AvailableSaveRequest;
import dev.patika.Veterinary.dto.request.availableDate.AvailableUpdateRequest;
import dev.patika.Veterinary.dto.response.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.entities.AvailableDate;
import org.springframework.data.domain.Page;
import java.time.LocalDate;

public interface IAvailableDateService {
    AvailableDateResponse save(AvailableSaveRequest availableDate);

    AvailableDate get(Long id);

    AvailableDateResponse getAvailableDateResponse(Long id);

    Page<AvailableDateResponse> cursor(int page, int pageSize);

    AvailableDateResponse update(AvailableUpdateRequest availableDate);

    boolean delete(Long id);

    boolean existsByAvailableDateAndDoctor_Id(LocalDate availableDate, Long doctorId);
}
