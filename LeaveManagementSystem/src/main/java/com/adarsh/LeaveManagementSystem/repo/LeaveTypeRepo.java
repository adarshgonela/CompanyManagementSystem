package com.adarsh.LeaveManagementSystem.repo;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveType,Integer> {
    LeaveType save(LeaveType leaveType, Long empid);
}
