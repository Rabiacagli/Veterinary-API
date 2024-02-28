package dev.patika.Veterinary.api;


import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.business.abstracts.IDoctorService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.appointment.AppointmentResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Appointment;
import dev.patika.Veterinary.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IAnimalService animalService, IDoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);
        // randevuya hayvan eklendi
        Animal animal = animalService.get(appointmentSaveRequest.getAnimalId());
        saveAppointment.setAnimal(animal);

        // randevuya doktor eklendı
        Doctor doctor = doctorService.get(appointmentSaveRequest.getDoctorId());
        saveAppointment.setDoctor(doctor);
        this.appointmentService.save(saveAppointment);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.appointmentService.get(id), AppointmentResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize

    ) {
        Page<Appointment> appointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

        return ResultHelper.cursor(appointmentResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {

        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class);
        this.appointmentService.update(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment, AppointmentResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }
}
