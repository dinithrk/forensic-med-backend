package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "specialization")
    private String specialization;

    @ElementCollection
    @CollectionTable(name = "staff_contacts", joinColumns = @JoinColumn(name = "staff_id"))
    @Column(name = "contact_no")
    private Set<String> contactNumbers;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
