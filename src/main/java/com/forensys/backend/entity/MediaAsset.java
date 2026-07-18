package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_asset")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    private String filePath;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no")
    private PostMortem postMortem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id")
    private MlefRecord mlefRecord;
}
