package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "identifier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deceased_id", nullable = false)
    private Deceased deceased;

    private String nicNumber;
    private String fullName;
    private String residingAddress;
}
