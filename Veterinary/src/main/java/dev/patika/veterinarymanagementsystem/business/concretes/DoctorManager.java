package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IDoctorRepo;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorManager implements IDoctorService {
    private final IDoctorRepo doctorRepo;
    private final IModelMapperService modelMapper;

    public DoctorManager(IDoctorRepo doctorRepo, IModelMapperService modelMapper) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorResponse save(DoctorSaveRequest doctorSave) {
        if (existsByNameAndPhoneAndMailAndAddressAndCity(
                doctorSave.getName(),
                doctorSave.getPhone(),
                doctorSave.getMail(),
                doctorSave.getAddress(),
                doctorSave.getCity())
        ) {
           throw new ConflictException("Doctor Already Exists");
        }
        Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSave, Doctor.class);
        this.doctorRepo.save(saveDoctor);
        return this.modelMapper.forResponse().map(saveDoctor, DoctorResponse.class);
    }

    @Override
    public Doctor get(Long id) {
        return this.doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public DoctorResponse getDoctorResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), DoctorResponse.class);
    }

    @Override
    public Page<DoctorResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.doctorRepo.findAll(pageable)
                .map(doctor -> this.modelMapper.forResponse()
                        .map(doctor, DoctorResponse.class));
    }

    @Override
    public DoctorResponse update(DoctorUpdateRequest doctorUpdate) {
        if (this.get(doctorUpdate.getDoctorId()) == null) {
            throw new NotFoundException("Doctor Not Found");
        }
        if (existsByNameAndPhoneAndMailAndAddressAndCity(
                doctorUpdate.getName(),
                doctorUpdate.getPhone(),
                doctorUpdate.getMail(),
                doctorUpdate.getAddress(),
                doctorUpdate.getCity())
        ) {
            throw new ConflictException("Doctor Already Exists");
        }

        Doctor doctor = this.modelMapper.forRequest().map(doctorUpdate, Doctor.class);
        this.get(doctor.getDoctorId());
        this.doctorRepo.save(doctor);
        return this.modelMapper.forResponse().map(doctor, DoctorResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        this.doctorRepo.delete(this.get(id));
        return true;
    }

    @Override
    public boolean existsByNameAndPhoneAndMailAndAddressAndCity(
            String name,
            String phone,
            String mail,
            String address,
            String city
    ) {
        return this.doctorRepo.existsByNameAndPhoneAndMailAndAddressAndCity
                (name, phone, mail, address, city);
    }
}
