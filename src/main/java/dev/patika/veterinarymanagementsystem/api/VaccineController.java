package dev.patika.veterinarymanagementsystem.api;


import dev.patika.veterinarymanagementsystem.business.abstracts.IVaccineService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineProtectionDateAndAnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSave) {
        return ResultHelper.created(this.vaccineService.save(vaccineSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.vaccineService.getVaccineResponse(id));
    }

    @GetMapping("/protect")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineProtectionDateAndAnimalResponse>> findByProtectionFinishDateBetween(
            @RequestParam(name = "start-date") LocalDate startDate,
            @RequestParam(name = "fnsh-date") LocalDate fnshDate
    ) {

        return ResultHelper.success(this.vaccineService.findByProtectionFinishDateBetween(startDate, fnshDate));
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.vaccineService.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update
            (@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest)
    {
        return ResultHelper.success(this.vaccineService.update(vaccineUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }
}
