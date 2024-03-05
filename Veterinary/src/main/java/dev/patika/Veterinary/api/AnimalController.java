package dev.patika.Veterinary.api;
import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.request.animal.AnimalUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        return ResultHelper.created(this.animalService.save(animalSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.animalService.getAnimalResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "7") int pageSize

    ) {
        return ResultHelper.cursor(this.animalService.cursor(page, pageSize));
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

    @GetMapping("/{animalId}/vaccines")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> filterVaccinesByAnimal(@PathVariable("animalId") Long animalId) {
        return ResultHelper.success(this.animalService.filterVaccinesByAnimal(animalId));
    }


    @GetMapping("/findName/{name}")
    @ResponseStatus(HttpStatus.OK)

    public ResultData<List<AnimalResponse>> filterAnimalsByName(@PathVariable("name") String name) {
        return ResultHelper.success(this.animalService.findByName(name));
    }
}
