package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.IAnimalService;
import dev.patika.Veterinary.business.abstracts.IVaccineService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.VaccineRepo;
import dev.patika.Veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.Veterinary.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.Veterinary.entities.Animal;
import dev.patika.Veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private  final IModelMapperService modelMapper;
    private final IAnimalService animalService;


    public VaccineManager(VaccineRepo vaccineRepo, IModelMapperService modelMapper, IAnimalService animalService) {
        this.vaccineRepo = vaccineRepo;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
    }

    @Override
    public VaccineResponse save(VaccineSaveRequest vaccineSave) {          // Değerlendirme 15
       if(this.existsByCodeAndProtectionFinishDateAfter(vaccineSave.getCode(), vaccineSave.getProtectionStartDate())){ // Değerlendirme 19
           throw new ConflictException("Vaccine already exists!");       // Değerlendirme 25
       }
        Animal animal = this.animalService.get(vaccineSave.getAnimalId());
        vaccineSave.setAnimalId(null);
        Vaccine vaccine = this.modelMapper.forRequest().map(vaccineSave, Vaccine.class);
        vaccine.setAnimal(animal);
        this.vaccineRepo.save(vaccine);
        return this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
    }

    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public VaccineResponse getVaccineResponse(Long id) {
        return this.modelMapper.forResponse().map(get(id),VaccineResponse.class);
    }

    @Override
    public Page<VaccineResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vaccine> vaccinePage = this.vaccineRepo.findAll(pageable);
        return vaccinePage.map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
    }

    @Override
    public VaccineResponse update(VaccineUpdateRequest vaccineUpdate) {
        if(this.existsByCodeAndProtectionFinishDateAfter(vaccineUpdate.getCode(), vaccineUpdate.getProtectionStartDate())){
            throw new ConflictException("Vaccine already exists!");  // Değerlendirme 25
        }
        Vaccine vaccineUp = this.modelMapper.forRequest().map(vaccineUpdate, Vaccine.class);
        this.get(vaccineUp.getId());
        this.vaccineRepo.save(vaccineUp);
        return this.modelMapper.forResponse().map(vaccineUp, VaccineResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    @Override
    public List<VaccineResponse> findByProtectionFinishDateBetween(
            LocalDate protectionStartDate, LocalDate protectionFinishDate) {  // Değerlendirme 21
        return this.vaccineRepo.findByProtectionFinishDateBetween(protectionStartDate, protectionFinishDate)
                .stream().map(vaccine -> this.modelMapper.forResponse()
                        .map(vaccine,VaccineResponse.class)).toList();

    }

    @Override
    public boolean existsByCodeAndProtectionFinishDateAfter(String code, LocalDate protectionStartDate) {  // Değerlendirme 19
        return this.vaccineRepo.existsByCodeAndProtectionFinishDateAfter(code, protectionStartDate);
    }
}

