package com.example.daily0220.repository;

import com.example.daily0220.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // 요구사항: 이름 검색 후 급여 내림차순 정렬
    List<Employee> findByEmpNameOrderBySalaryDesc(String name);
}
