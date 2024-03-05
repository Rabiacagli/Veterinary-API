package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.business.abstracts.IDoctorService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.AvailableDateRepo;
import dev.patika.Veterinary.dto.request.availableDate.AvailableSaveRequest;
import dev.patika.Veterinary.dto.request.availableDate.AvailableUpdateRequest;
import dev.patika.Veterinary.dto.response.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.entities.AvailableDate;
import dev.patika.Veterinary.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, IModelMapperService modelMapper, IDoctorService doctorService) {
        this.availableDateRepo = availableDateRepo;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }

    @Override
    public AvailableDateResponse save(AvailableSaveRequest availableSave) {          // Değerlendirme 13
        if (this.availableDateRepo.existsByAvailableDateAndDoctor_Id(availableSave.getAvailableDate(),availableSave.getDoctorId())) {
            throw new ConflictException("There is no availability. Please choose another day.");     // Değerlendirme 25
        }
        Doctor doctor = this.doctorService.get(availableSave.getDoctorId());
        availableSave.setDoctorId(null);
        AvailableDate availableDate = this.modelMapper.forRequest().map(availableSave, AvailableDate.class);
        availableDate.setDoctor(doctor);
        this.availableDateRepo.save(availableDate);
        return this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public AvailableDateResponse getAvailableDateResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id),AvailableDateResponse.class);
    }

    @Override
    public Page<AvailableDateResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AvailableDate> availableDatePage = this.availableDateRepo.findAll(pageable);
        return availableDatePage
                .map(availableDate -> this.modelMapper.forResponse()
                        .map(availableDate,AvailableDateResponse.class));
    }

    @Override
    public AvailableDateResponse update(AvailableUpdateRequest availableUpdate) {
        if (this.availableDateRepo.existsByAvailableDateAndDoctor_Id(availableUpdate.getAvailableDate(),availableUpdate.getDoctorId())) {
            throw new ConflictException("There is no availability. Please choose another day.");       // Değerlendirme 25
        }
        AvailableDate availableDateUp = this.modelMapper.forRequest().map(availableUpdate,AvailableDate.class);
        Doctor doctor = this.doctorService.get(availableUpdate.getDoctorId());
        availableDateUp.setDoctor(doctor);
        this.get(availableDateUp.getId());
        this.availableDateRepo.save(availableDateUp);
        return this.modelMapper.forResponse().map(availableDateUp,AvailableDateResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public boolean existsByAvailableDateAndDoctor_Id(LocalDate availableDate, Long doctorId) {
        return this.availableDateRepo.existsByAvailableDateAndDoctor_Id(availableDate,doctorId);
    }
}
