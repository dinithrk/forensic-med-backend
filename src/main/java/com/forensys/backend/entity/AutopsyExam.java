package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "autopsy_exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutopsyExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autopsyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no")
    private PostMortem postMortem;

    private String autopsyReportPdf;
    private String health1135aDoc;
    private String maternalDeathCategory;
    private Boolean underInvestigation;

    @ElementCollection
    @CollectionTable(name = "autopsy_cause_of_death", joinColumns = @JoinColumn(name = "autopsy_id"))
    private Set<CauseOfDeath> causesOfDeath;

    @ElementCollection
    @CollectionTable(name = "autopsy_comment", joinColumns = @JoinColumn(name = "autopsy_id"))
    @Column(name = "comment")
    private Set<String> comments;
}
