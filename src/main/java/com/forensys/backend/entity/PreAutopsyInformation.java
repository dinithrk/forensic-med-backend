package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pre_autopsy_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreAutopsyInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no", nullable = false)
    private PostMortem postMortem;

    private String recordDetails;
    private String informationCategory;
}
