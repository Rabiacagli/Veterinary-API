package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IReportService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IVaccineService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IVaccineRepo;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineProtectionDateAndAnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Report;
import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class VaccineManager implements IVaccineService {
    private final IVaccineRepo vaccineRepo;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IReportService reportService;


    public VaccineManager(IVaccineRepo vaccineRepo, IModelMapperService modelMapper, IAnimalService animalService, IReportService reportService) {
        this.vaccineRepo = vaccineRepo;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.reportService = reportService;
    }


    @Override
    public VaccineResponse save(VaccineSaveRequest vaccineSave) {

        if (existsByVaccineCodeAndProtectionFinishDateAfter(
                vaccineSave.getVaccineCode(),                           //// Değerlendirme 19
                vaccineSave.getProtectionStartDate())
        ) {

            throw new ConflictException
                    ("Vaccine Already Exists! Please Check Vaccine Code and Protection Start Date!");

        } else {
            Animal animal = animalService.get(vaccineSave.getAnimalId());
            Report report = reportService.get(vaccineSave.getReportId());
            vaccineSave.setAnimalId(null);
            vaccineSave.setReportId(null);
            Vaccine vaccine = this.modelMapper.forRequest().map(vaccineSave, Vaccine.class);
            vaccine.setAnimal(animal);
            vaccine.setReport(report);
            this.vaccineRepo.save(vaccine);
            return this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        }
    }

    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public VaccineResponse getVaccineResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), VaccineResponse.class);
    }

    @Override
    public Page<VaccineResponse> cursor(int page, int pageSize) {
        Page<Vaccine> vaccinePage = this.vaccineRepo.findAll(PageRequest.of(page, pageSize));
        return vaccinePage.map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
    }

    @Override
    public VaccineResponse update(VaccineUpdateRequest vaccineUpdate) {

        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdate, Vaccine.class);
        Animal animal = animalService.get(vaccineUpdate.getAnimalId());
        Report report = reportService.get(vaccineUpdate.getReportId());
        updateVaccine.setAnimal(animal);
        updateVaccine.setReport(report);
        this.get(updateVaccine.getVaccineId());
        this.vaccineRepo.save(updateVaccine);
        return this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        this.vaccineRepo.delete(this.get(id));
        return true;
    }

    @Override                                               // Değerlendirme 21
    public List<VaccineProtectionDateAndAnimalResponse> findByProtectionFinishDateBetween(
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<Vaccine> vaccineList = this.vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);
        return vaccineList.stream()
                .map(vaccine -> this.modelMapper.forResponse()
                        .map(vaccine, VaccineProtectionDateAndAnimalResponse.class)).toList();

        // Vaccine Listesini VaccineProtectionDateAndAnimalResponse listesine map edip döndürdüm
    }

    @Override
    public Boolean existsByVaccineCodeAndProtectionFinishDateAfter(
            String vaccineCode,
            LocalDate protectionStartDate
    ) {
        return this.vaccineRepo.existsByVaccineCodeAndProtectionFinishDateAfter(vaccineCode, protectionStartDate);
    }
}
