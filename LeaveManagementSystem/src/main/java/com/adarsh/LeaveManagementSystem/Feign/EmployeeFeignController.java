package com.adarsh.LeaveManagementSystem.Feign;

import com.adarsh.LeaveManagementSystem.refDto.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient("EMPLOYEEMANAGEMENTSYSTEM")
public interface EmployeeFeignController {
    @GetMapping("/api/employees/all")
    public List<Employee> getallemployees();

    @PostMapping("api/employees/post/{id}")
    public Optional<Employee> getEmployeeByIdpost(@PathVariable Long id);

    
}
