package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "case_photograph")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CasePhotograph extends Photograph {
}
