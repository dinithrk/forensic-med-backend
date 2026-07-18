package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "referral")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;

    private String referredToConsultant;
    private String specialty;
    private String referralReason;
    private Boolean reportReceivedBack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id", nullable = false)
    private MlefRecord mlefRecord;
}
