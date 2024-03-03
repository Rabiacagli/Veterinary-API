package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);

    AvailableDate get(Long id);

    Page<AvailableDate> cursor(int page, int pageSize);

    AvailableDate update(AvailableDate availableDate);

    boolean delete(Long id);

    boolean existsByAvailableDateAndDoctor_Id(LocalDate availableDate, Long doctorId);
}
