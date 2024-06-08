package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface IAvailableDateServices {
    AvailableDateResponse save(AvailableDateSaveRequest availableDate);

    AvailableDate get(Long id);
    AvailableDateResponse getAvailableDateResponse(Long id);

    Page<AvailableDateResponse> cursor(int page, int pageSize);

    AvailableDateResponse update(AvailableDateUpdateRequest availableDate);

    boolean delete(Long id);

    Boolean existsByAvailableDateAndDoctor_DoctorId
            (LocalDate availableDate, Long doctorId);
}
