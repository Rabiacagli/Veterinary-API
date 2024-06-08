package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)

    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSave) {
        return ResultHelper.created(this.customerService.save(customerSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.customerService.getCustomerResponse(id));
    }

    @GetMapping("/{id}/animals")  // Değerlendirme 18
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimals(@PathVariable("id") Long id) {
        return ResultHelper.success(this.customerService.getAnimals(id));
    }

    @GetMapping("/byname/{name}") // Değerlendirme 17
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getByName(@PathVariable("name") String name) {
        return ResultHelper.success(this.customerService.getByName(name));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.customerService.cursor(page,pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return ResultHelper.success( this.customerService.update(customerUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.customerService.delete(id);
        return ResultHelper.ok();
    }
}
