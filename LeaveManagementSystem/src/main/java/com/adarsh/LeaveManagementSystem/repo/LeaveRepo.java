package com.adarsh.LeaveManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;

public interface LeaveRepo extends JpaRepository<LeaveRequest,Long> {

}
