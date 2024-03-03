package dev.patika.Veterinary.api;

import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.ICustomerService;
import dev.patika.Veterinary.business.abstracts.IVaccineService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.result.Result;
import dev.patika.Veterinary.core.result.ResultData;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.core.utilies.ResultHelper;
import dev.patika.Veterinary.dao.AnimalRepo;
import dev.patika.Veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.Veterinary.dto.request.animal.AnimalUpdateRequest;
import dev.patika.Veterinary.dto.response.CursorResponse;
import dev.patika.Veterinary.dto.response.animal.AnimalResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Customer;
import dev.patika.Veterinary.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static dev.patika.Veterinary.core.utilies.Msg.NOT_FOUND;


@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;
    private final IVaccineService vaccineService;


    @Autowired
    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService, IVaccineService vaccineService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.vaccineService = vaccineService;

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        return ResultHelper.created(this.animalService.save(animalSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.animalService.get(id), AnimalResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "7") int pageSize

    ) {
        Page<Animal> animalPage = this.animalService.cursor(page, pageSize);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));

        return ResultHelper.cursor(animalResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {

        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class);
        this.animalService.update(updateAnimal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal, AnimalResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/{animalId}/vaccines")  //hayvanın aşılarını getirir
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Vaccine>> filterVaccinesByAnimal(@PathVariable("animalId") Long animalId) {
        return ResultHelper.success(this.animalService.filterVaccinesByAnimal(animalId));
    }


    @GetMapping("/filterByName/{name}")
    @ResponseStatus(HttpStatus.OK)

    public ResultData<List<Animal>> filterAnimalsByName(@PathVariable("name") String name) {
        return ResultHelper.success(this.animalService.findByName(name));
    }





}
