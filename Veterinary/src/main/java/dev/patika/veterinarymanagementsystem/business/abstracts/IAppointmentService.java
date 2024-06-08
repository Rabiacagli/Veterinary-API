package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.appointment.AppointmentResponse;
import dev.patika.veterinarymanagementsystem.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    AppointmentResponse save(AppointmentSaveRequest appointmentSave);

    Appointment get(Long id);

    AppointmentResponse getAppointmentResponse(Long id);

    Page<AppointmentResponse> cursor(int page, int pageSize);

    AppointmentResponse update(AppointmentUpdateRequest appointmentUpdate);

    boolean delete(Long id);

    List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_DoctorId
            (LocalDate startDate,
             LocalDate endDate,
             Long doctorId);

    List<AppointmentResponse> findByAppointmentDateBetweenAndAnimal_animalId
            (LocalDate startDate,
             LocalDate endDate,
             Long animalId);

    Boolean existsByAppointmentDate(LocalDateTime appointmentDate);
}
