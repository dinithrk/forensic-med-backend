package com.forensys.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forensys.backend.dto.ForensicReportDto;
import com.forensys.backend.dto.ReportNotificationDto;
import com.forensys.backend.dto.ReportStatusUpdateDto;
import com.forensys.backend.entity.Deceased;
import com.forensys.backend.entity.ForensicReport;
import com.forensys.backend.entity.MlefRecord;
import com.forensys.backend.entity.Patient;
import com.forensys.backend.entity.PostMortem;
import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.entity.enums.ReportType;
import com.forensys.backend.repository.DeceasedRepository;
import com.forensys.backend.repository.ForensicReportRepository;
import com.forensys.backend.repository.MlefRecordRepository;
import com.forensys.backend.repository.PostMortemRepository;
import com.forensys.backend.service.ReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ForensicReportRepository reportRepository;
    private final MlefRecordRepository mlefRecordRepository;
    private final PostMortemRepository postMortemRepository;
    private final DeceasedRepository deceasedRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public ForensicReportDto autoGenerateReportDraft(String caseType, Long caseId, String reportTypeStr) {
        ReportType reportType;
        try {
            reportType = ReportType.valueOf(reportTypeStr.toUpperCase());
        } catch (Exception e) {
            reportType = "POSTMORTEM".equalsIgnoreCase(caseType) ? ReportType.PMR : ReportType.MLR;
        }

        ForensicReport report = ForensicReport.builder()
                .caseType(caseType.toUpperCase())
                .reportType(reportType)
                .status(ReportStatus.DRAFT)
                .versionNumber(1)
                .draftDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map<String, Object> detailsMap = new HashMap<>();

        if ("CLINICAL".equalsIgnoreCase(caseType)) {
            MlefRecord mlef = mlefRecordRepository.findById(caseId)
                    .orElseThrow(() -> new EntityNotFoundException("MLEF Record not found: " + caseId));
            report.setMlefRecord(mlef);
            report.setExaminationDate(mlef.getDateTimeExamined());
            report.setPoliceRefNo(mlef.getPoliceRefNo());
            report.setSerialNo(reportType == ReportType.CERTIFICATE_OF_RECEIPT ?
                    "CER-MLEF-" + mlef.getMlefId() : "MLR-2026-" + String.format("%04d", mlef.getMlefId()));

            Patient patient = mlef.getPatient();
            if (patient != null) {
                detailsMap.put("patientName", patient.getFullName());
                String nicOrPassport = patient.getIdentification() != null ?
                        (patient.getIdentification().getNicNo() != null ? patient.getIdentification().getNicNo() : patient.getIdentification().getPassportNo()) : "N/A";
                detailsMap.put("patientNic", nicOrPassport);
                detailsMap.put("patientAge", patient.getAge());
                detailsMap.put("patientSex", patient.getSex() != null ? patient.getSex().name() : "");
                detailsMap.put("patientAddress", patient.getAddress());
                detailsMap.put("hospitalNo", mlef.getHospitalBhtNo());
            }

            detailsMap.put("shortHistory", mlef.getShortHistoryGivenByPatient());
            detailsMap.put("placeExamined", mlef.getPlaceExamined());
            detailsMap.put("reasonForReferral", mlef.getReasonForReferral());
            detailsMap.put("hospitalName", mlef.getHospitalName());
            detailsMap.put("hospitalWard", mlef.getHospitalWard());
            detailsMap.put("hospitalBhtNo", mlef.getHospitalBhtNo());
            detailsMap.put("remarks", mlef.getRemarks());

            // Injuries payload summary
            if (mlef.getInjuries() != null) {
                List<Map<String, String>> injuryList = new ArrayList<>();
                mlef.getInjuries().forEach(inj -> {
                    Map<String, String> iMap = new HashMap<>();
                    String type = inj.getNatureOfBodilyHarm() != null ? inj.getNatureOfBodilyHarm().name() : inj.getInjuryClass();
                    String weapon = inj.getSpecificWeaponName() != null ? inj.getSpecificWeaponName() : (inj.getWeaponCategory() != null ? inj.getWeaponCategory().name() : "N/A");
                    iMap.put("type", type != null ? type : "N/A");
                    iMap.put("size", inj.getDetailedDescription() != null ? inj.getDetailedDescription() : "N/A");
                    iMap.put("placement", inj.getDiagramTagLabel() != null ? inj.getDiagramTagLabel() : "N/A");
                    iMap.put("weapon", weapon);
                    iMap.put("observations", inj.getRemarks() != null ? inj.getRemarks() : (inj.getExplanatoryRemarks() != null ? inj.getExplanatoryRemarks() : "-"));
                    injuryList.add(iMap);
                });
                detailsMap.put("injuries", injuryList);
            }

            if (mlef.getAssignedMedicalOfficer() != null) {
                report.setDoctorName(mlef.getAssignedMedicalOfficer().getFullName());
                report.setDoctorDesignation(mlef.getAssignedMedicalOfficer().getDesignation());
                report.setDoctorSlmcNo(mlef.getAssignedMedicalOfficer().getSlmcRegNo());
            } else {
                report.setDoctorName("Dr. JMO Officer");
                report.setDoctorDesignation("Judicial Medical Officer");
            }

            report.setOpinion("Based on clinical findings and injury documentation, the injuries described are compatible with the history provided.");

        } else if ("POSTMORTEM".equalsIgnoreCase(caseType)) {
            PostMortem pm = postMortemRepository.findById(caseId)
                    .orElseThrow(() -> new EntityNotFoundException("Postmortem Record not found: " + caseId));
            report.setPostMortem(pm);
            report.setExaminationDate(pm.getDateTimeOfPmExam());
            report.setSerialNo(reportType == ReportType.CERTIFICATE_OF_RECEIPT ?
                    "CER-PM-" + pm.getPmSerialNo() : "PMR-2026-" + String.format("%04d", pm.getPmSerialNo()));

            Deceased deceased = pm.getDeceased();
            if (deceased != null) {
                detailsMap.put("deceasedName", deceased.getFullName());
                detailsMap.put("deceasedAge", deceased.getAgeWhenDied());
                detailsMap.put("deceasedSex", deceased.getSex() != null ? deceased.getSex().name() : "");
                detailsMap.put("deceasedAddress", deceased.getLastAddress());
                detailsMap.put("placeOfDeath", deceased.getPlaceOfDeath());
                detailsMap.put("hospitalName", deceased.getHospitalName());
                detailsMap.put("bhtNo", deceased.getBhtNo());
                detailsMap.put("dateOfDeath", deceased.getDateOfDeath());

                if (deceased.getInquestOrder() != null) {
                    report.setCourtName(deceased.getInquestOrder().getCourt());
                    report.setCourtCaseNo(deceased.getInquestOrder().getCaseNo());
                    report.setPoliceStation(deceased.getInquestOrder().getPoliceStationArea());
                    detailsMap.put("inquirerName", deceased.getInquestOrder().getInquirerFullName());
                    detailsMap.put("magistrate", deceased.getInquestOrder().getMagistrate());
                }
            }

            if (pm.getAutopsyExam() != null && pm.getAutopsyExam().getCausesOfDeath() != null) {
                List<Map<String, String>> causesList = new ArrayList<>();
                pm.getAutopsyExam().getCausesOfDeath().forEach(cod -> {
                    Map<String, String> cMap = new HashMap<>();
                    cMap.put("description", cod.getCauseDescription());
                    cMap.put("severity", cod.getSeverity());
                    cMap.put("onset", cod.getAproxTFromOnsetToDeath());
                    causesList.add(cMap);
                });
                detailsMap.put("causesOfDeath", causesList);
            }

            if (pm.getMedicalOfficers() != null && !pm.getMedicalOfficers().isEmpty()) {
                var doc = pm.getMedicalOfficers().iterator().next();
                report.setDoctorName(doc.getFullName());
                report.setDoctorDesignation(doc.getDesignation());
                report.setDoctorSlmcNo(doc.getSlmcRegNo());
            } else {
                report.setDoctorName("Dr. Chief Forensic Pathologist");
                report.setDoctorDesignation("Senior Consultant JMO");
            }

            report.setOpinion("In my opinion, death was due to multiple injuries/natural causes as stated above, subject to histopathology and toxicology results.");
        }

        try {
            report.setDetailsJson(objectMapper.writeValueAsString(detailsMap));
        } catch (Exception e) {
            report.setDetailsJson("{}");
        }

        ForensicReport saved = reportRepository.save(report);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public ForensicReportDto saveReport(ForensicReportDto dto) {
        ForensicReport report;
        if (dto.getId() != null) {
            report = reportRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Report not found: " + dto.getId()));
        } else {
            report = new ForensicReport();
            report.setStatus(ReportStatus.DRAFT);
            report.setVersionNumber(1);
            report.setDraftDate(LocalDateTime.now());
        }

        report.setReportType(dto.getReportType());
        report.setCaseType(dto.getCaseType());
        report.setSerialNo(dto.getSerialNo());
        report.setCourtName(dto.getCourtName());
        report.setCourtCaseNo(dto.getCourtCaseNo());
        report.setPoliceStation(dto.getPoliceStation());
        report.setPoliceRefNo(dto.getPoliceRefNo());
        report.setDateOfTrial(dto.getDateOfTrial());
        report.setExaminationDate(dto.getExaminationDate());
        report.setOpinion(dto.getOpinion());
        report.setDetailsJson(dto.getDetailsJson());
        report.setDoctorName(dto.getDoctorName());
        report.setDoctorDesignation(dto.getDoctorDesignation());
        report.setDoctorSlmcNo(dto.getDoctorSlmcNo());

        if (dto.getMlefRecordId() != null) {
            mlefRecordRepository.findById(dto.getMlefRecordId()).ifPresent(report::setMlefRecord);
        }
        if (dto.getPmSerialNo() != null) {
            postMortemRepository.findById(dto.getPmSerialNo()).ifPresent(report::setPostMortem);
        }

        ForensicReport saved = reportRepository.save(report);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ForensicReportDto getReportById(Long id) {
        ForensicReport report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + id));
        return mapToDto(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForensicReportDto> getAllReports(ReportStatus status) {
        List<ForensicReport> reports = (status != null) ?
                reportRepository.findByStatus(status) : reportRepository.findAll();
        return reports.stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForensicReportDto> getReportsForCase(String caseType, Long caseId) {
        List<ForensicReport> reports;
        if ("POSTMORTEM".equalsIgnoreCase(caseType)) {
            reports = reportRepository.findByPostMortem_PmSerialNoOrderByVersionNumberDescCreatedAtDesc(caseId);
        } else {
            reports = reportRepository.findByMlefRecord_MlefIdOrderByVersionNumberDescCreatedAtDesc(caseId);
        }
        return reports.stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public ForensicReportDto updateReportStatus(Long id, ReportStatusUpdateDto updateDto) {
        ForensicReport report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + id));

        ReportStatus newStatus = updateDto.getStatus();
        report.setStatus(newStatus);
        LocalDateTime now = LocalDateTime.now();

        switch (newStatus) {
            case FINALIZED -> {
                if (report.getFinalizedDate() == null) report.setFinalizedDate(now);
            }
            case DISPATCHED -> {
                if (report.getFinalizedDate() == null) report.setFinalizedDate(now);
                if (report.getDispatchedDate() == null) report.setDispatchedDate(now);
            }
            case RECEIPT_CONFIRMED -> {
                if (report.getFinalizedDate() == null) report.setFinalizedDate(now);
                if (report.getDispatchedDate() == null) report.setDispatchedDate(now);
                if (report.getReceiptConfirmedDate() == null) report.setReceiptConfirmedDate(now);
            }
            default -> {}
        }

        ForensicReport updated = reportRepository.save(report);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public ForensicReportDto amendReport(Long id, String amendmentReason, ForensicReportDto dto) {
        ForensicReport original = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + id));

        ForensicReport amended = ForensicReport.builder()
                .reportType(original.getReportType())
                .status(ReportStatus.DRAFT)
                .versionNumber(original.getVersionNumber() + 1)
                .caseType(original.getCaseType())
                .mlefRecord(original.getMlefRecord())
                .postMortem(original.getPostMortem())
                .serialNo(original.getSerialNo() + "-v" + (original.getVersionNumber() + 1))
                .courtName(dto.getCourtName() != null ? dto.getCourtName() : original.getCourtName())
                .courtCaseNo(dto.getCourtCaseNo() != null ? dto.getCourtCaseNo() : original.getCourtCaseNo())
                .policeStation(dto.getPoliceStation() != null ? dto.getPoliceStation() : original.getPoliceStation())
                .policeRefNo(dto.getPoliceRefNo() != null ? dto.getPoliceRefNo() : original.getPoliceRefNo())
                .dateOfTrial(dto.getDateOfTrial() != null ? dto.getDateOfTrial() : original.getDateOfTrial())
                .examinationDate(original.getExaminationDate())
                .opinion(dto.getOpinion() != null ? dto.getOpinion() : original.getOpinion())
                .detailsJson(dto.getDetailsJson() != null ? dto.getDetailsJson() : original.getDetailsJson())
                .doctorName(dto.getDoctorName() != null ? dto.getDoctorName() : original.getDoctorName())
                .doctorDesignation(dto.getDoctorDesignation() != null ? dto.getDoctorDesignation() : original.getDoctorDesignation())
                .doctorSlmcNo(dto.getDoctorSlmcNo() != null ? dto.getDoctorSlmcNo() : original.getDoctorSlmcNo())
                .draftDate(LocalDateTime.now())
                .parentReportId(original.getId())
                .amendmentReason(amendmentReason)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ForensicReport saved = reportRepository.save(amended);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportNotificationDto getNotificationWidgetData() {
        LocalDate today = LocalDate.now();

        // 1. Overdue reports calculation
        List<ReportNotificationDto.OverdueReportItem> overdueItems = new ArrayList<>();

        // Check MLEF records without finalized reports
        List<MlefRecord> mlefRecords = mlefRecordRepository.findAll();
        for (MlefRecord mlef : mlefRecords) {
            List<ForensicReport> reports = reportRepository.findByMlefRecord_MlefIdOrderByVersionNumberDescCreatedAtDesc(mlef.getMlefId());
            boolean isFinalizedOrDispatched = reports.stream().anyMatch(r -> r.getStatus() == ReportStatus.FINALIZED || r.getStatus() == ReportStatus.DISPATCHED || r.getStatus() == ReportStatus.RECEIPT_CONFIRMED);
            
            if (!isFinalizedOrDispatched) {
                LocalDateTime examDate = mlef.getDateTimeExamined() != null ? mlef.getDateTimeExamined() : LocalDateTime.now().minusDays(10);
                long daysElapsed = ChronoUnit.DAYS.between(examDate.toLocalDate(), today);
                if (daysElapsed >= 3) {
                    String subjectName = mlef.getPatient() != null ? mlef.getPatient().getFullName() : "Patient #" + mlef.getPatient().getPatientId();
                    String doctor = mlef.getAssignedMedicalOfficer() != null ? mlef.getAssignedMedicalOfficer().getFullName() : "Unassigned";
                    overdueItems.add(ReportNotificationDto.OverdueReportItem.builder()
                            .caseType("CLINICAL")
                            .caseId(mlef.getMlefId())
                            .referenceNo("MLEF-" + mlef.getMlefId())
                            .subjectName(subjectName)
                            .examinationDate(examDate)
                            .daysOverdue(daysElapsed)
                            .assignedDoctor(doctor)
                            .build());
                }
            }
        }

        // Check Postmortem records without finalized reports
        List<PostMortem> pmRecords = postMortemRepository.findAll();
        for (PostMortem pm : pmRecords) {
            List<ForensicReport> reports = reportRepository.findByPostMortem_PmSerialNoOrderByVersionNumberDescCreatedAtDesc(pm.getPmSerialNo());
            boolean isFinalizedOrDispatched = reports.stream().anyMatch(r -> r.getStatus() == ReportStatus.FINALIZED || r.getStatus() == ReportStatus.DISPATCHED || r.getStatus() == ReportStatus.RECEIPT_CONFIRMED);

            if (!isFinalizedOrDispatched) {
                LocalDateTime examDate = pm.getDateTimeOfPmExam() != null ? pm.getDateTimeOfPmExam() : LocalDateTime.now().minusDays(14);
                long daysElapsed = ChronoUnit.DAYS.between(examDate.toLocalDate(), today);
                if (daysElapsed >= 3) {
                    String subjectName = pm.getDeceased() != null ? pm.getDeceased().getFullName() : "Deceased #" + pm.getDeceased().getDeceasedId();
                    String doctor = (pm.getMedicalOfficers() != null && !pm.getMedicalOfficers().isEmpty()) ?
                            pm.getMedicalOfficers().iterator().next().getFullName() : "Unassigned";
                    overdueItems.add(ReportNotificationDto.OverdueReportItem.builder()
                            .caseType("POSTMORTEM")
                            .caseId(pm.getPmSerialNo())
                            .referenceNo("PM-" + pm.getPmSerialNo())
                            .subjectName(subjectName)
                            .examinationDate(examDate)
                            .daysOverdue(daysElapsed)
                            .assignedDoctor(doctor)
                            .build());
                }
            }
        }

        overdueItems.sort((a, b) -> Long.compare(b.getDaysOverdue(), a.getDaysOverdue()));

        // 2. Upcoming Court Dates
        List<ForensicReport> courtReports = reportRepository.findUpcomingCourtDates(today);
        List<ReportNotificationDto.UpcomingCourtCaseItem> courtItems = courtReports.stream().map(r -> {
            long daysUntil = ChronoUnit.DAYS.between(today, r.getDateOfTrial());
            Long caseId = "POSTMORTEM".equalsIgnoreCase(r.getCaseType()) && r.getPostMortem() != null ?
                    r.getPostMortem().getPmSerialNo() : (r.getMlefRecord() != null ? r.getMlefRecord().getMlefId() : 0L);
            return ReportNotificationDto.UpcomingCourtCaseItem.builder()
                    .reportId(r.getId())
                    .caseType(r.getCaseType())
                    .caseId(caseId)
                    .courtName(r.getCourtName())
                    .courtCaseNo(r.getCourtCaseNo())
                    .dateOfTrial(r.getDateOfTrial())
                    .daysUntilTrial(daysUntil)
                    .subjectName(getSubjectNameFromReport(r))
                    .build();
        }).toList();

        // 3. Finalized Reports Pending Dispatch
        List<ForensicReport> pendingDispatchesList = reportRepository.findPendingDispatches();
        List<ReportNotificationDto.PendingDispatchItem> dispatchItems = pendingDispatchesList.stream().map(r -> {
            Long caseId = "POSTMORTEM".equalsIgnoreCase(r.getCaseType()) && r.getPostMortem() != null ?
                    r.getPostMortem().getPmSerialNo() : (r.getMlefRecord() != null ? r.getMlefRecord().getMlefId() : 0L);
            return ReportNotificationDto.PendingDispatchItem.builder()
                    .reportId(r.getId())
                    .reportType(r.getReportType().name())
                    .serialNo(r.getSerialNo())
                    .caseType(r.getCaseType())
                    .caseId(caseId)
                    .courtName(r.getCourtName())
                    .courtCaseNo(r.getCourtCaseNo())
                    .finalizedDate(r.getFinalizedDate())
                    .doctorName(r.getDoctorName())
                    .build();
        }).toList();

        return ReportNotificationDto.builder()
                .overdueReports(overdueItems)
                .upcomingCourtDates(courtItems)
                .pendingDispatches(dispatchItems)
                .build();
    }

    private ForensicReportDto mapToDto(ForensicReport r) {
        Long mlefId = r.getMlefRecord() != null ? r.getMlefRecord().getMlefId() : null;
        Long pmId = r.getPostMortem() != null ? r.getPostMortem().getPmSerialNo() : null;

        return ForensicReportDto.builder()
                .id(r.getId())
                .reportType(r.getReportType())
                .status(r.getStatus())
                .versionNumber(r.getVersionNumber())
                .caseType(r.getCaseType())
                .mlefRecordId(mlefId)
                .pmSerialNo(pmId)
                .serialNo(r.getSerialNo())
                .courtName(r.getCourtName())
                .courtCaseNo(r.getCourtCaseNo())
                .policeStation(r.getPoliceStation())
                .policeRefNo(r.getPoliceRefNo())
                .dateOfTrial(r.getDateOfTrial())
                .examinationDate(r.getExaminationDate())
                .opinion(r.getOpinion())
                .detailsJson(r.getDetailsJson())
                .doctorName(r.getDoctorName())
                .doctorDesignation(r.getDoctorDesignation())
                .doctorSlmcNo(r.getDoctorSlmcNo())
                .draftDate(r.getDraftDate())
                .finalizedDate(r.getFinalizedDate())
                .dispatchedDate(r.getDispatchedDate())
                .receiptConfirmedDate(r.getReceiptConfirmedDate())
                .parentReportId(r.getParentReportId())
                .amendmentReason(r.getAmendmentReason())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .subjectName(getSubjectNameFromReport(r))
                .build();
    }

    private String getSubjectNameFromReport(ForensicReport r) {
        if (r.getMlefRecord() != null && r.getMlefRecord().getPatient() != null) {
            return r.getMlefRecord().getPatient().getFullName();
        } else if (r.getPostMortem() != null && r.getPostMortem().getDeceased() != null) {
            return r.getPostMortem().getDeceased().getFullName();
        }
        return "N/A";
    }
}
