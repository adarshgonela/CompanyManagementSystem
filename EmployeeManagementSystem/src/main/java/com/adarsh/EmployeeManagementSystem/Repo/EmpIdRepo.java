package com.adarsh.EmployeeManagementSystem.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.EmployeeManagementSystem.dto.EmployeeId;

public interface EmpIdRepo extends JpaRepository<EmployeeId, Long> {

    

}
