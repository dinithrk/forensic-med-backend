package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pm_photo")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PmPhoto extends Photograph {
}
