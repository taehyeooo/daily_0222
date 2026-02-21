package com.example.daily0220.repository;

import com.example.daily0220.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmpNameOrderBySalaryDesc(String name);
}
