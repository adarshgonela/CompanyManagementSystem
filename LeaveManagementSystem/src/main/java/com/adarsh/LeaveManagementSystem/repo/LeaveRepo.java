package com.adarsh.LeaveManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepo extends JpaRepository<LeaveRequest,Long> {

    @Query(value = "SELECT * FROM leave_table WHERE leave_request_id = :employeeId", nativeQuery = true)
    List<LeaveRequest> findByEmployee(Long employeeId);

}
