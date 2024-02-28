package dev.patika.Veterinary.api;

import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.IVaccineService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.Veterinary.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;

    @Autowired
    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapper, IAnimalService animalService) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        Animal animal = animalService.get(vaccineSaveRequest.getAnimalId());
        saveVaccine.setAnimal(animal);
        saveVaccine.setId(null);
        this.vaccineService.save(saveVaccine);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.vaccineService.get(id), VaccineResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize

    ) {
        Page<Vaccine> vaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(vaccineResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {

        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        this.vaccineService.update(updateVaccine);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }
}
