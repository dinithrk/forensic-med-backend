package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "court_request")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private LocalDateTime dateAndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no")
    private PostMortem postMortem;
}
