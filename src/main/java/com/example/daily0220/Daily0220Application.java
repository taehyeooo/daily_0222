package com.example.daily0220;

import com.example.daily0220.domain.*;
import com.example.daily0220.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Daily0220Application implements CommandLineRunner {

    @Autowired private EmployeeRepository empRepo;
    @Autowired private DepartmentRepository deptRepo;

    public static void main(String[] args) {
        SpringApplication.run(Daily0220Application.class, args);
    }

    @Override
    public void run(String... args) {
        Department d = deptRepo.save(new Department("기술지원팀", "인천"));
        empRepo.save(new Employee("김태형", 5500L, d));
        empRepo.save(new Employee("이정연", 7500L, d));
        empRepo.save(new Employee("김태형", 4500L, d)); // 정렬 테스트용

        System.out.println("\n--- [전체 사원 정보 조회] ---");
        empRepo.findAll().forEach(e ->
                System.out.println("사번: " + e.getEmpNo() + ", 이름: " + e.getEmpName() + ", 부서: " + e.getDepartment().getDeptName()));

        System.out.println("\n--- ['김태형' 검색 결과 - 급여 내림차순] ---");
        List<Employee> result = empRepo.findByEmpNameOrderBySalaryDesc("김태형");
        result.forEach(e ->
                System.out.println("이름: " + e.getEmpName() + ", 급여: " + e.getSalary() + ", 입사일: " + e.getHireDate()));
    }
}
