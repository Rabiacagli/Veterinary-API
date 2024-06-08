package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAppointmentService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/appointmentdates")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSave) {
        return ResultHelper.created(this.appointmentService.save(appointmentSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.appointmentService.getAppointmentResponse(id));
    }

    @GetMapping("/filter-date-doctor")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> filterByDateAndDoctor(
            @RequestParam(name = "start-date") LocalDate startDate,
            @RequestParam(name = "fnsh-date") LocalDate finishDate,
            @RequestParam(name = "doctorId") Long doctorId
    ) {
        return ResultHelper.success
                (this.appointmentService.findByAppointmentDateBetweenAndDoctor_DoctorId
                        (startDate, finishDate, doctorId));
    }

    @GetMapping("/filter-date-animal")
    public ResultData<List<AppointmentResponse>> filterByDateAndAnimal(
            @RequestParam(name = "start-date") LocalDate startDate,
            @RequestParam(name = "fnsh-date") LocalDate finishDate,
            @RequestParam(name = "animalId") Long animalId
    ) {
        return ResultHelper.success(this.appointmentService.
                findByAppointmentDateBetweenAndAnimal_animalId(startDate, finishDate, animalId));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.appointmentService.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update
            (@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {

        return ResultHelper.success(this.appointmentService.update(appointmentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }
}

