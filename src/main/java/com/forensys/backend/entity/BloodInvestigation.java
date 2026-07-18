package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blood_investigation")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BloodInvestigation extends Investigation {
}
