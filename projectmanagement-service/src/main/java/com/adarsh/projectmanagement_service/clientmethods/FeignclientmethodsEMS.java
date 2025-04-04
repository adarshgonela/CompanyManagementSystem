package com.adarsh.projectmanagement_service.clientmethods;

import com.adarsh.projectmanagement_service.dto.EmployeeClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient("EMPLOYEEMANAGEMENTSYSTEM")
public interface FeignclientmethodsEMS {

    @PostMapping("api/employees/post/{id}")
    public Optional<EmployeeClient> getEmployeeByIdpost(@PathVariable Long id);
}
