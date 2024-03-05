package dev.patika.Veterinary.api;
import dev.patika.Veterinary.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.Veterinary.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;


    @Autowired
    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        return ResultHelper.created(this.appointmentService.save(appointmentSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.appointmentService.getAppointmentResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize

    ) {
        return ResultHelper.cursor(this.appointmentService.cursor(page,pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){
        return ResultHelper.success(this.appointmentService.update(appointmentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/byDrId")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> filterByDateAndDoctor(
            @RequestParam(name = "start-date")LocalDate startDate,
            @RequestParam(name = "finish-date")LocalDate finishDate,
            @RequestParam(name = "doctor-id")Long doctorId
    ){
        return ResultHelper.success(this.appointmentService.findByAppointmentDateBetweenAndDoctor_Id(startDate,finishDate,doctorId));
    }

    @GetMapping("/byAnimalId")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> filterByDateAndAnimal(
            @RequestParam(name = "start-date")LocalDate startDate,
            @RequestParam(name = "finish-date")LocalDate finishDate,
            @RequestParam(name = "animal-id")Long animalId
    ){
        return ResultHelper.success(this.appointmentService.findByAppointmentDateBetweenAndAnimal_Id(startDate,finishDate,animalId));
    }
}
