package dev.patika.Veterinary.business.abstracts;

import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.response.appointment.AppointmentResponse;
import dev.patika.Veterinary.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    AppointmentResponse save(AppointmentSaveRequest appointment);

    Appointment get(Long id);

    Page<Appointment> cursor(int page, int pageSize);

    Appointment update(Appointment appointment);

    boolean delete(Long id);

    List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_Id(LocalDate startDate,LocalDate finishDate,Long doctorId);

    List <AppointmentResponse>  findByAppointmentDateBetweenAndAnimal_Id(LocalDate startDate, LocalDate finishDate, Long animalId);

    boolean existByAppointmentDate(LocalDateTime appointmentDate);


}
