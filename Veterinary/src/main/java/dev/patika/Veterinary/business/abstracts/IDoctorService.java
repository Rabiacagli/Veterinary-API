package dev.patika.Veterinary.business.abstracts;
import dev.patika.Veterinary.dto.request.doctor.DoctorSaveRequest;
import dev.patika.Veterinary.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.Veterinary.dto.response.doctor.DoctorResponse;
import dev.patika.Veterinary.entities.Doctor;
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
