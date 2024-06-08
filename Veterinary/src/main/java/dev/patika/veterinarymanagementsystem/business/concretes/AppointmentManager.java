package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IAppointmentService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IAvailableDateServices;
import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IAppointmentRepo;
import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.appointment.AppointmentResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Appointment;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final IAppointmentRepo appointmentRepo;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;
    private final IAvailableDateServices availableDateServices;

    public AppointmentManager
            (IAppointmentRepo appointmentRepo,
             IModelMapperService modelMapper,
             IAnimalService animalService,
             IDoctorService doctorService,
             IAvailableDateServices availableDateServices)

    {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
        this.availableDateServices = availableDateServices;
    }


    @Override                                   // Değerlendirme 22
    public AppointmentResponse save(AppointmentSaveRequest appointmentSaveRequest) {


        if ((existsByAppointmentDate(appointmentSaveRequest.getAppointmentDate()))
                || !(this.availableDateServices.existsByAvailableDateAndDoctor_DoctorId
                (LocalDate.from(appointmentSaveRequest.getAppointmentDate()),appointmentSaveRequest.getDoctorId()))
                || appointmentSaveRequest.getAppointmentDate().getMinute() != 0) {
            throw new ConflictException("Veterinarian is not available at this time or the appointment time is not valid");

        } else {
            Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);
            Animal animal = animalService.get(appointmentSaveRequest.getAnimalId());
            Doctor doctor = doctorService.get(appointmentSaveRequest.getDoctorId());
            saveAppointment.setAnimal(animal);
            saveAppointment.setDoctor(doctor);
            this.appointmentRepo.save(saveAppointment);
            return this.modelMapper.forResponse().map(saveAppointment,AppointmentResponse.class);
        }
    }

    @Override           // Değerlendirme 25
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public AppointmentResponse getAppointmentResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id),AppointmentResponse.class);
    }

    @Override
    public Page<AppointmentResponse> cursor(int page, int pageSize) {
        Page<Appointment> appointmentPage = this.appointmentRepo.findAll(PageRequest.of(page, pageSize));
        return appointmentPage.map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

    }

    @Override
    public AppointmentResponse update(AppointmentUpdateRequest appointmentUpdate) {

        if ((existsByAppointmentDate(appointmentUpdate.getAppointmentDate()))
                || !(this.availableDateServices.existsByAvailableDateAndDoctor_DoctorId
                (LocalDate.from(appointmentUpdate.getAppointmentDate()),appointmentUpdate.getDoctorId()))
                || appointmentUpdate.getAppointmentDate().getMinute() != 0) {
            throw new ConflictException("Veterinarian is not available at this time or the appointment time is not valid");
        }

        Appointment appointment = this.modelMapper.forRequest().map(appointmentUpdate, Appointment.class);
        Animal animal = animalService.get(appointmentUpdate.getAnimalId());
        Doctor doctor = doctorService.get(appointmentUpdate.getDoctorId());
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        this.get(appointment.getAppointmentId());
        this.appointmentRepo.save(appointment);
        return this.modelMapper.forResponse().map(appointment,AppointmentResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        this.appointmentRepo.delete(this.get(id));
        return true;
    }

    @Override                       // Değerlendirme 24
    public List<AppointmentResponse> findByAppointmentDateBetweenAndDoctor_DoctorId
            (LocalDate startDate,
             LocalDate endDate,
             Long doctorId)

    {
        LocalDateTime startDayTime = startDate.atStartOfDay();
        LocalDateTime finishDayTime = endDate.atStartOfDay().plusDays(1);

        List<Appointment> appointments = this.appointmentRepo.
                findByAppointmentDateBetweenAndDoctor_DoctorId(startDayTime,finishDayTime,doctorId);
        return appointments.stream().
                map(appointment -> this.modelMapper.forResponse().
                        map(appointment,AppointmentResponse.class)).toList();
    }

    @Override                        // Değerlendirme 23
    public List<AppointmentResponse> findByAppointmentDateBetweenAndAnimal_animalId
            (LocalDate startDate,
             LocalDate endDate,
             Long animalId)

    {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime finishDateTime = endDate.atStartOfDay().plusDays(1);

        List<Appointment> appointments = this.appointmentRepo
                .findByAppointmentDateBetweenAndAnimal_animalId(startDateTime,finishDateTime,animalId);
        return appointments.stream()
                .map(appointment -> this.modelMapper.forResponse()
                        .map(appointment,AppointmentResponse.class)).toList();
    }

    @Override
    public Boolean existsByAppointmentDate(LocalDateTime appointmentDate) {
        return this.appointmentRepo.existsByAppointmentDate(appointmentDate);
    }


}
