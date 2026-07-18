package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "photograph")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Photograph extends MediaAsset {
    private LocalDate captureDate;
    private String description;
    private String caption;
}
