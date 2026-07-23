package com.forensys.backend.config;

import com.forensys.backend.entity.*;
import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.entity.enums.ReportType;
import com.forensys.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MlefRecordRepository mlefRecordRepository;
    private final PostMortemRepository postMortemRepository;
    private final ForensicReportRepository forensicReportRepository;
    private final MedicalOfficerRepository medicalOfficerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedRoles();
        seedUsers();
        seedSampleAnalyticsData();
    }

    private void seedRoles() {
        List<String> rolesToSeed = Arrays.asList(
                "ADMIN", "JMO", "MEDICAL_OFFICER", "LABORATORY_STAFF", "CLERICAL_OFFICER", "POLICE_OFFICER"
        );

        for (String roleName : rolesToSeed) {
            if (roleRepository.findByRoleName(roleName).isEmpty()) {
                Role role = Role.builder().roleName(roleName).build();
                roleRepository.save(role);
            }
        }
    }

    private void seedUsers() {
        String encodedPassword = passwordEncoder.encode("password");

        seedUserIfNotFound("admin", "ADMIN", encodedPassword);
        seedUserIfNotFound("jmo", "JMO", encodedPassword);
        seedUserIfNotFound("doctor", "MEDICAL_OFFICER", encodedPassword);
        seedUserIfNotFound("lab", "LABORATORY_STAFF", encodedPassword);
        seedUserIfNotFound("clerk", "CLERICAL_OFFICER", encodedPassword);
        seedUserIfNotFound("police", "POLICE_OFFICER", encodedPassword);
    }

    private void seedUserIfNotFound(String username, String roleName, String encodedPassword) {
        if (userRepository.findByUserName(username).isEmpty()) {
            Role role = roleRepository.findByRoleName(roleName).orElseThrow();
            User user = User.builder()
                    .userName(username)
                    .password(encodedPassword)
                    .roles(Set.of(role))
                    .build();
            userRepository.save(user);
        }
    }

    private void seedSampleAnalyticsData() {
        if (mlefRecordRepository.count() >= 5) {
            return; // Data already seeded
        }

        MedicalOfficer doc1 = MedicalOfficer.builder().fullName("Dr. S. K. Perera (JMO)").slmcRegNo("SLMC-12345").designation("Senior JMO").build();
        MedicalOfficer doc2 = MedicalOfficer.builder().fullName("Dr. M. A. Fernando (JMO)").slmcRegNo("SLMC-67890").designation("JMO Specialist").build();
        MedicalOfficer doc3 = MedicalOfficer.builder().fullName("Dr. K. L. Wickramasinghe").slmcRegNo("SLMC-54321").designation("Assistant JMO").build();
        medicalOfficerRepository.saveAll(Arrays.asList(doc1, doc2, doc3));

        LocalDateTime now = LocalDateTime.now();

        // Sample Clinical Cases & Reports spanning last 6 months
        String[] categories = {"Physical Assault / Blunt Force", "Road Traffic Accident", "Sexual Assault", "Domestic Violence", "Burns & Scalds"};
        MedicalOfficer[] docs = {doc1, doc2, doc3};

        for (int i = 0; i < 25; i++) {
            int monthsAgo = i % 6;
            int daysOffset = (i * 3) % 25;
            LocalDateTime examDate = now.minusMonths(monthsAgo).minusDays(daysOffset);

            MlefRecord mlef = MlefRecord.builder()
                    .policeRefNo("POL-2026-" + (1000 + i))
                    .reasonForReferral(categories[i % categories.length])
                    .dateTimeExamined(examDate)
                    .hospitalName("Colombo National Hospital")
                    .assignedMedicalOfficer(docs[i % docs.length])
                    .build();
            mlefRecordRepository.save(mlef);

            ReportStatus status = (i % 3 == 0) ? ReportStatus.DRAFT : ((i % 3 == 1) ? ReportStatus.FINALIZED : ReportStatus.DISPATCHED);
            LocalDateTime finalizedDate = examDate.plusDays(1 + (i % 4));
            LocalDateTime dispatchedDate = status == ReportStatus.DISPATCHED ? finalizedDate.plusDays(1) : null;

            ForensicReport report = ForensicReport.builder()
                    .caseType("CLINICAL")
                    .reportType(ReportType.MLR)
                    .status(status)
                    .versionNumber(1)
                    .mlefRecord(mlef)
                    .serialNo("MLR-2026-" + String.format("%04d", mlef.getMlefId()))
                    .policeStation("Colombo Central")
                    .policeRefNo(mlef.getPoliceRefNo())
                    .doctorName(docs[i % docs.length].getFullName())
                    .doctorDesignation(docs[i % docs.length].getDesignation())
                    .examinationDate(examDate)
                    .draftDate(examDate)
                    .finalizedDate(finalizedDate)
                    .dispatchedDate(dispatchedDate)
                    .createdAt(examDate)
                    .updatedAt(finalizedDate)
                    .build();
            forensicReportRepository.save(report);
        }

        // Sample PostMortem Cases & Reports
        for (int j = 0; j < 15; j++) {
            int monthsAgo = j % 6;
            int daysOffset = (j * 4) % 25;
            LocalDateTime pmDate = now.minusMonths(monthsAgo).minusDays(daysOffset);

            PostMortem pm = PostMortem.builder()
                    .dateTimeOfPmExam(pmDate)
                    .placeOfExamination("Mortuary - Judicial Medical Office")
                    .district("Colombo")
                    .medicalOfficers(Set.of(docs[j % docs.length]))
                    .build();
            postMortemRepository.save(pm);

            ReportStatus status = (j % 2 == 0) ? ReportStatus.FINALIZED : ReportStatus.DISPATCHED;
            LocalDateTime finalizedDate = pmDate.plusDays(2 + (j % 3));
            LocalDateTime dispatchedDate = status == ReportStatus.DISPATCHED ? finalizedDate.plusDays(1) : null;

            ForensicReport pmReport = ForensicReport.builder()
                    .caseType("POSTMORTEM")
                    .reportType(ReportType.PMR)
                    .status(status)
                    .versionNumber(1)
                    .postMortem(pm)
                    .serialNo("PMR-2026-" + String.format("%04d", pm.getPmSerialNo()))
                    .policeStation("Maradana Police")
                    .policeRefNo("POL-PM-2026-" + (500 + j))
                    .doctorName(docs[j % docs.length].getFullName())
                    .doctorDesignation(docs[j % docs.length].getDesignation())
                    .examinationDate(pmDate)
                    .draftDate(pmDate)
                    .finalizedDate(finalizedDate)
                    .dispatchedDate(dispatchedDate)
                    .createdAt(pmDate)
                    .updatedAt(finalizedDate)
                    .build();
            forensicReportRepository.save(pmReport);
        }
    }
}
