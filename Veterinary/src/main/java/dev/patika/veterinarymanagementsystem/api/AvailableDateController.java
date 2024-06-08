package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAvailableDateServices;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.availableDate.AvailableDateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availabledates")
public class AvailableDateController {
    private final IAvailableDateServices availableDateServices;

    public AvailableDateController(IAvailableDateServices availableDateServices) {
        this.availableDateServices = availableDateServices;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSave) {
        return ResultHelper.created(this.availableDateServices.save(availableDateSave));

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.availableDateServices.getAvailableDateResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.availableDateServices.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update
            (@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        return ResultHelper.success(this.availableDateServices.update(availableDateUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateServices.delete(id);
        return ResultHelper.ok();
    }
}

