package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.dto.request.report.ReportSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.report.ReportUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.report.ReportResponse;

import dev.patika.veterinarymanagementsystem.entities.Report;
import org.springframework.data.domain.Page;

public interface IReportService {

    ReportResponse save(ReportSaveRequest reportSave);

    Report get(Long id);

    ReportResponse getReportResponse(Long id);

    Page<ReportResponse> cursor(int page, int pageSize);

    ReportResponse update(ReportUpdateRequest reportUpdateRequest);

    boolean delete(Long id);
}
