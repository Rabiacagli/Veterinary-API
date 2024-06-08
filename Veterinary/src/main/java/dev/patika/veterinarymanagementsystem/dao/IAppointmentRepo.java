package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByAppointmentDateBetweenAndDoctor_DoctorId
            (LocalDateTime startDate,
             LocalDateTime endDate,
             Long doctorId);

    List<Appointment> findByAppointmentDateBetweenAndAnimal_animalId
            (LocalDateTime startDate,
             LocalDateTime endDate,
             Long animalId);

    Boolean existsByAppointmentDate
            (LocalDateTime appointmentDate);

}
