package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSave) {
        return ResultHelper.created(this.doctorService.save(doctorSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.doctorService.getDoctorResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.doctorService.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {


        return ResultHelper.success(this.doctorService.update(doctorUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.doctorService.delete(id);
        return ResultHelper.ok();
    }
}

