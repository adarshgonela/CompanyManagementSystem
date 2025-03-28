package com.adarsh.LeaveManagementSystem.repo;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveType,Integer> {
    @Query("select l from LeaveType l where l.empid = :empid")
    Optional<LeaveType> getLeaveTypeByEmpId(@Param("empid") Long empid);


}
