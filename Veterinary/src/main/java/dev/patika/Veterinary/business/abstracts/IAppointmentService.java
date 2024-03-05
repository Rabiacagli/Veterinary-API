package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.Veterinary.dto.response.appointment.AppointmentResponse;
import dev.patika.Veterinary.entities.Appointment;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    AppointmentResponse save(AppointmentSaveRequest appointment);

    Appointment get(Long id);

    AppointmentResponse getAppointmentResponse(Long id);

    Page<AppointmentResponse> cursor(int page, int pageSize);

    AppointmentResponse update(AppointmentUpdateRequest appointment);

    boolean delete(Long id);

    List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_Id(
            LocalDate startDate,LocalDate finishDate,Long doctorId);

    List <AppointmentResponse>  findByAppointmentDateBetweenAndAnimal_Id(
            LocalDate startDate, LocalDate finishDate, Long animalId);

}
