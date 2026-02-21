package com.example.daily0220.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empNo;
    private String empName;
    private Long salary;

    @Column(updatable = false)
    private LocalDateTime hireDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptNo")
    private Department department;

    public Employee() {}
    public Employee(String empName, Long salary, Department department) {
        this.empName = empName;
        this.salary = salary;
        this.department = department;
    }
    public Long getEmpNo() { return empNo; }
    public String getEmpName() { return empName; }
    public Long getSalary() { return salary; }
    public LocalDateTime getHireDate() { return hireDate; }
    public Department getDepartment() { return department; }
}
