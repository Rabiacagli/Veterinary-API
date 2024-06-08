package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.domain.Page;

public interface IDoctorService {
    DoctorResponse save(DoctorSaveRequest doctor);

    Doctor get(Long id);

    DoctorResponse getDoctorResponse(Long id);

    Page<DoctorResponse> cursor(int page, int pageSize);

    DoctorResponse update(DoctorUpdateRequest doctor);

    boolean delete(Long id);

    boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city);
}

