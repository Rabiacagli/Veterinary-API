package dev.patika.Veterinary.api;

import dev.patika.Veterinary.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.availableDate.AvailableSaveRequest;
import dev.patika.Veterinary.dto.request.availableDate.AvailableUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.entities.AvailableDate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;

    @Autowired
    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableSaveRequest availableSaveRequest) {
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableSaveRequest, AvailableDate.class);
        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.availableDateService.get(id), AvailableDateResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize

    ) {
        Page<AvailableDate> availableDatePage = this.availableDateService.cursor(page, pageSize);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));

        return ResultHelper.cursor(availableDateResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableUpdateRequest availableUpdateRequest) {

        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableUpdateRequest, AvailableDate.class);
        this.availableDateService.update(updateAvailableDate);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAvailableDate, AvailableDateResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }
}
