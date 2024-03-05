package dev.patika.Veterinary.business.concretes;
import dev.patika.Veterinary.business.abstracts.IDoctorService;
import dev.patika.Veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.Veterinary.core.exception.ConflictException;
import dev.patika.Veterinary.core.exception.NotFoundException;
import dev.patika.Veterinary.core.utilies.Msg;
import dev.patika.Veterinary.dao.DoctorRepo;
import dev.patika.Veterinary.dto.request.doctor.DoctorSaveRequest;
import dev.patika.Veterinary.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.Veterinary.dto.response.doctor.DoctorResponse;
import dev.patika.Veterinary.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;
    private final IModelMapperService modelMapper;


    public DoctorManager(DoctorRepo doctorRepo, IModelMapperService modelMapper) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorResponse save(DoctorSaveRequest doctorSave) {          // Değerlendirme 12
        if (this.existsByNameAndPhoneAndMailAndAddressAndCity(doctorSave.getName(),
                doctorSave.getPhone(),doctorSave.getMail(),doctorSave.getAddress(),doctorSave.getCity())) {
            throw new ConflictException("Doctor already exists, please change informations and try again");  // Değerlendirme 25
        }

        Doctor doctor = this.modelMapper.forRequest().map(doctorSave, Doctor.class);
        this.doctorRepo.save(doctor);
        return this.modelMapper.forResponse().map(doctor,DoctorResponse.class);
    }

    @Override
    public Doctor get(Long id) {
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public DoctorResponse getDoctorResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), DoctorResponse.class);
    }

    @Override
    public Page<DoctorResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Doctor> doctorPage = this.doctorRepo.findAll(pageable);
        return doctorPage.map(doctor -> this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
    }

    @Override
    public DoctorResponse update(DoctorUpdateRequest doctorUpdate) {
        if (this.existsByNameAndPhoneAndMailAndAddressAndCity(doctorUpdate.getName(),
                doctorUpdate.getPhone(),
                doctorUpdate.getMail(),
                doctorUpdate.getAddress(),
                doctorUpdate.getCity())) {
            throw new ConflictException("Doctor already exists, please change informations and try again");  // Değerlendirme 25
        }
        Doctor doctorUp = this.modelMapper.forRequest().map(doctorUpdate,Doctor.class);
        this.get(doctorUp.getId());
        this.doctorRepo.save(doctorUp);
        return this.modelMapper.forResponse().map(doctorUp, DoctorResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public boolean existsByNameAndPhoneAndMailAndAddressAndCity(
            String name, String phone, String mail, String address, String city) {
        return this.doctorRepo.existsByNameAndPhoneAndMailAndAddressAndCity(name, phone, mail, address, city);
    }
}

