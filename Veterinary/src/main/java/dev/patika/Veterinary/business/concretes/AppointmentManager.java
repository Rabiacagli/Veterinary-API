package dev.patika.Veterinary.business.concretes;

import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.business.abstracts.IDoctorService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AppointmentRepo;
import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.response.appointment.AppointmentResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Appointment;
import dev.patika.Veterinary.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;

    private final IModelMapperService modelMapper;

    private final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;

    public AppointmentManager(AppointmentRepo appointmentRepo, IModelMapperService modelMapper, IAvailableDateService availableDateService, IDoctorService doctorService, IAnimalService animalService) {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public AppointmentResponse save(AppointmentSaveRequest saveAppointment) {
        if(!(this.availableDateService.existsByAvailableDateAndDoctor_Id(LocalDate.from(saveAppointment.getAppointmentDate()), saveAppointment.getDoctorId()))
                || (this.appointmentRepo.existsByAppointmentDate(saveAppointment.getAppointmentDate()))
                || saveAppointment.getAppointmentDate().getMinute() != 0
        ){
            throw new NotFoundException("Doktor musaıt degıl ");
        }else{
            Appointment appointment = this.modelMapper.forRequest().map(saveAppointment, Appointment.class);
            Animal animal = this.animalService.get(saveAppointment.getAnimalId());
            Doctor doctor = this.doctorService.get(saveAppointment.getDoctorId());
            appointment.setAnimal(animal);
            appointment.setDoctor(doctor);
            this.appointmentRepo.save(appointment);
            return this.modelMapper.forResponse().map(appointment, AppointmentResponse.class);
        }
    }
    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public boolean delete(Long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_Id(LocalDate startDate, LocalDate finishDate, Long doctorId) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime finishDateTime = finishDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByAppointmentDateBetweenAndDoctor_Id(startDateTime, finishDateTime, doctorId);
        return appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment, AppointmentResponse.class)).toList();
    }

    @Override
    public List<AppointmentResponse> findByAppointmentDateBetweenAndAnimal_Id(LocalDate startDate, LocalDate finishDate, Long animalId) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime finishDateTime = finishDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByAppointmentDateBetweenAndAnimal_Id(startDateTime, finishDateTime, animalId);
        return appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment, AppointmentResponse.class)).toList();
    }

    @Override
    public boolean existByAppointmentDate(LocalDateTime appointmentDate) {
        return this.appointmentRepo.existsByAppointmentDate(appointmentDate);
    }
}

