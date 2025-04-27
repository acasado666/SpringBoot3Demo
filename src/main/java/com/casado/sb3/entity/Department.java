package com.casado.sb3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", unique = true)
    private String name;

    @Column(name = "department_code", unique = true)
    private String departmentCode;

    private String description;

    @Column(name = "department_location", unique = true)
    private String building;

    private String phoneNumber;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;
}