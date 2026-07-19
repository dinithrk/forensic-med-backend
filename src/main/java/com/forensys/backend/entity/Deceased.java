package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "deceased")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deceased {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deceasedId;

    private String fullName;
    private Integer ageWhenDied;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    private String lastAddress;
    private String placeOfDeath;

    // Hospital Details
    private String hospitalName;
    private String bhtNo;
    private String wardNo;

    private LocalDate dateOfDeath;
    private LocalTime timeOfDeath;

    @OneToOne(mappedBy = "deceased", cascade = CascadeType.ALL, orphanRemoval = true)
    private InquestOrder inquestOrder;

    @OneToMany(mappedBy = "deceased", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Identifier> identifiers;
}
