package dev.patika.Veterinary.dao;


import dev.patika.Veterinary.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByAppointmentDateBetweenAndDoctor_Id(LocalDateTime startDate,LocalDateTime finishDate,Long doctorId);
   List <Appointment> findByAppointmentDateBetweenAndAnimal_Id(LocalDateTime startDate, LocalDateTime finishDate, Long animalId);

   boolean existsByAppointmentDate(LocalDateTime appointmentDate);
}
