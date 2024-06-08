package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAppointmentService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IReportService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.core.utilies.Msg;
import dev.patika.veterinarymanagementsystem.dao.IReportRepo;
import dev.patika.veterinarymanagementsystem.dto.request.report.ReportSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.report.ReportUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.report.ReportResponse;
import dev.patika.veterinarymanagementsystem.entities.Appointment;
import dev.patika.veterinarymanagementsystem.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportManager implements IReportService {
    private final IReportRepo reportRepo;
    private final IModelMapperService modelMapper;
    private final IAppointmentService appointmentService;

    public ReportManager(IReportRepo reportRepo, IModelMapperService modelMapper, IAppointmentService appointmentService) {
        this.reportRepo = reportRepo;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
    }

    @Override
    public ReportResponse save(ReportSaveRequest reportSave) {
        Appointment appointment = this.appointmentService.get(reportSave.getAppointmentId());
        reportSave.setAppointmentId(null);
        Report saveReport = this.modelMapper.forRequest().map(reportSave, Report.class);
        saveReport.setAppointment(appointment);
        this.reportRepo.save(saveReport);
        return this.modelMapper.forResponse().map(saveReport, ReportResponse.class);
    }

    @Override
    public Report get(Long id) {
        return this.reportRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ReportResponse getReportResponse(Long id) {
        return this.modelMapper.forResponse().map(this.get(id), ReportResponse.class);
    }

    @Override
    public Page<ReportResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.reportRepo.findAll(pageable)
                .map(report -> this.modelMapper.forResponse()
                        .map(report, ReportResponse.class));
    }

    @Override
    public ReportResponse update(ReportUpdateRequest reportUpdate) {
        Appointment appointment = this.appointmentService.get(reportUpdate.getAppointmentId());
        reportUpdate.setAppointmentId(null);
        Report updateReport = this.modelMapper.forRequest().map(reportUpdate, Report.class);
        updateReport.setAppointment(appointment);
        this.reportRepo.saveAndFlush(updateReport);
        return this.modelMapper.forResponse().map(updateReport, ReportResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        this.reportRepo.delete(this.get(id));
        return true;
    }
}
