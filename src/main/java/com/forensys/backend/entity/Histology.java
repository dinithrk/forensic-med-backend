package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "histology")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Histology extends Investigation {
}
