package dev.patika.Veterinary.api;
import dev.patika.Veterinary.business.abstracts.IVaccineService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.Veterinary.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    @Autowired
    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        return ResultHelper.created(this.vaccineService.save(vaccineSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.vaccineService.getVaccineResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        return ResultHelper.cursor(this.vaccineService.cursor(page,pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        return ResultHelper.success(this.vaccineService.update(vaccineUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/protection")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> findByProtectionFinishDateBetween
            (@RequestParam(name = "protectionStartDate") LocalDate protectionStartDate,
             @RequestParam(name = "protectionFinishDate") LocalDate protectionFinishDate) {
        return ResultHelper.success(this.vaccineService
                .findByProtectionFinishDateBetween(protectionStartDate, protectionFinishDate));
    }
}
