package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAvailableDateServices;
import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.ConflictException;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IAvailableDateRepo;
import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AvailableDateManager implements IAvailableDateServices {
    private final IAvailableDateRepo availableDateRepo;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateManager
            (IAvailableDateRepo availableDateRepo,
             IModelMapperService modelMapper,
             IDoctorService doctorService)

    {
        this.availableDateRepo = availableDateRepo;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }
    @Override
    public AvailableDateResponse save(AvailableDateSaveRequest availableDateSave) {
        if (existsByAvailableDateAndDoctor_DoctorId
                (availableDateSave.getAvailableDate(), availableDateSave.getDoctorId()))
        {
            throw new ConflictException("Available Date Already Exist");
        }
        Doctor doctor = doctorService.get(availableDateSave.getDoctorId());
        availableDateSave.setDoctorId(null);
        AvailableDate saveAvailableDate = this.modelMapper.forRequest()
                .map(availableDateSave, AvailableDate.class);
        saveAvailableDate.setDoctor(doctor);
        this.availableDateRepo.save(saveAvailableDate);

        return this.modelMapper.forResponse()
                .map(saveAvailableDate, AvailableDateResponse.class);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public AvailableDateResponse getAvailableDateResponse(Long id) {
        return this.modelMapper.forResponse()
                .map(this.get(id), AvailableDateResponse.class);
    }

    @Override
    public Page<AvailableDateResponse> cursor(int page, int pageSize)
    {
        return this.availableDateRepo.findAll(PageRequest.of(page,pageSize))
                .map(availableDate -> this.modelMapper.forResponse()
                        .map(availableDate,AvailableDateResponse.class));
    }

    @Override
    public AvailableDateResponse update
            (AvailableDateUpdateRequest availableDateUpdateRequest)
    {
        if (existsByAvailableDateAndDoctor_DoctorId
                (availableDateUpdateRequest.getAvailableDate(), availableDateUpdateRequest.getDoctorId()))
        {
            throw new ConflictException("Available Date Already Exist");
        }

        AvailableDate availableDate = this.modelMapper.forRequest()
                .map(availableDateUpdateRequest, AvailableDate.class);
        this.get(availableDate.getAvailableDateId());
        this.availableDateRepo.save(availableDate);
        return this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        this.availableDateRepo.delete(this.get(id));
        return true;
    }

    @Override
    public Boolean existsByAvailableDateAndDoctor_DoctorId
            (LocalDate availableDate,
             Long doctorId)
    {
        return this.availableDateRepo.existsByAvailableDateAndDoctor_DoctorId(availableDate, doctorId);
    }


}
