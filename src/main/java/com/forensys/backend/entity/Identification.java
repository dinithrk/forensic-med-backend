package com.forensys.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Identification {

    @Column(name = "nic_no")
    private String nicNo;

    @Column(name = "passport_no")
    private String passportNo;
}
