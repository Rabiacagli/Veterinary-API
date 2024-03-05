package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.business.abstracts.IDoctorService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AppointmentRepo;
import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.request.appointment.AppointmentUpdateRequest;
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

    public AppointmentManager(AppointmentRepo appointmentRepo,
                              IModelMapperService modelMapper,
                              IAvailableDateService availableDateService,
                              IDoctorService doctorService,
                              IAnimalService animalService) {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public AppointmentResponse save(AppointmentSaveRequest saveAppointment) {               // Değerlendirme 14
        if(!(this.availableDateService.existsByAvailableDateAndDoctor_Id(
                LocalDate.from(saveAppointment.getAppointmentDate()), saveAppointment.getDoctorId()))
                || (this.appointmentRepo.existsByAppointmentDate(saveAppointment.getAppointmentDate()))
                || saveAppointment.getAppointmentDate().getMinute() != 0                  // Değerlendirme 22
        ){
            throw new ConflictException("Doctor is not available at this time or the appointment time is not valid "); // Değerlendirme 25
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
    public AppointmentResponse getAppointmentResponse(Long id) {
        return modelMapper.forResponse().map(this.get(id),AppointmentResponse.class);
    }

    @Override
    public Page<AppointmentResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Appointment> appointmentPage = this.appointmentRepo.findAll(pageable);
        return appointmentPage
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment,AppointmentResponse.class));
    }

    @Override
    public AppointmentResponse update(AppointmentUpdateRequest appointmentUpdate) {
        if(!(this.availableDateService.existsByAvailableDateAndDoctor_Id(
                LocalDate.from(appointmentUpdate.getAppointmentDate()), appointmentUpdate.getDoctorId()))
                || (this.appointmentRepo.existsByAppointmentDate(appointmentUpdate.getAppointmentDate()))
                || appointmentUpdate.getAppointmentDate().getMinute() != 0
        ){
            throw new ConflictException("Doctor is not available at this time or the appointment time is not valid "); // Değerlendirme 25
        }
        Appointment appointment = this.modelMapper.forRequest().map(appointmentUpdate,Appointment.class);
        Doctor doctor = this.doctorService.get(appointmentUpdate.getDoctorId());
        Animal animal = this.animalService.get(appointmentUpdate.getAnimalId());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        this.get(appointment.getId());
        this.appointmentRepo.save(appointment);
        return this.modelMapper.forResponse().map(appointment, AppointmentResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_Id(
            LocalDate startDate,
            LocalDate finishDate,
            Long doctorId) { // Değerlendirme 24
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime finishDateTime = finishDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByAppointmentDateBetweenAndDoctor_Id(
                startDateTime, finishDateTime, doctorId);
        return appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment, AppointmentResponse.class)).toList();
    }

    @Override
    public List<AppointmentResponse> findByAppointmentDateBetweenAndAnimal_Id(
            LocalDate startDate, LocalDate finishDate, Long animalId) { // Değerlendirme 23
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime finishDateTime = finishDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByAppointmentDateBetweenAndAnimal_Id(
                startDateTime, finishDateTime, animalId);
        return appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment, AppointmentResponse.class)).toList();
    }
}

