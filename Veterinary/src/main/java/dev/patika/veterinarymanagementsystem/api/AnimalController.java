package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSave) {
        return ResultHelper.created(this.animalService.save(animalSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.animalService.getAnimalResponse(id));
    }

    @GetMapping("/{id}/vaccines")  // Değerlendirme 20
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccines(@PathVariable("id") Long id) {
        return ResultHelper.success(this.animalService.getVaccines(id));
    }

    @GetMapping("/byname/{name}") // Değerlendirme 16
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getByName(@PathVariable("name") String name) {
        return ResultHelper.success(this.animalService.getByName(name));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.animalService.cursor(page,pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        return ResultHelper.success(this.animalService.update(animalUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.ok();
    }
}

