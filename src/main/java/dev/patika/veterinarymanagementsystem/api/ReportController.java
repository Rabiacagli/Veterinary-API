package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IReportService;
import dev.patika.veterinarymanagementsystem.core.result.Result;
import dev.patika.veterinarymanagementsystem.core.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.utilies.ResultHelper;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.request.report.ReportSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.report.ReportUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.report.ReportResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {
    private final IReportService reportService;
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ReportResponse> save(@Valid @RequestBody ReportSaveRequest reportSave) {
        return ResultHelper.created(this.reportService.save(reportSave));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ReportResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.reportService.getReportResponse(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<ReportResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "1000") int pageSize

    ) {
        return ResultHelper.cursor(this.reportService.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ReportResponse> update(@Valid @RequestBody ReportUpdateRequest reportUpdateRequest) {


        return ResultHelper.success(this.reportService.update(reportUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.reportService.delete(id);
        return ResultHelper.ok();
    }
}

