package com.example.daily0220.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptNo;
    private String deptName;
    private String location;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    public Department() {}
    public Department(String deptName, String location) {
        this.deptName = deptName;
        this.location = location;
    }

    public Long getDeptNo() { return deptNo; }
    public String getDeptName() { return deptName; }
    public String getLocation() { return location; }
}
