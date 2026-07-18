package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "action_description", unique = true, nullable = false)
    private String actionDescription;
}
