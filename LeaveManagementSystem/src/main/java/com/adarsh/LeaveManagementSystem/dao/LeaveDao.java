package com.adarsh.LeaveManagementSystem.dao;

import java.util.List;
import java.util.Optional;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.repo.LeaveRepo;

@Repository
public class LeaveDao {

    @Autowired
    private LeaveRepo leaveRepo;

    public LeaveRequest requestleaveOrsaveleave(LeaveRequest leaverequest) {
        return leaveRepo.save(leaverequest);
    }

    public List<LeaveRequest> getallleave() {
        return leaveRepo.findAll();
    }

    public LeaveRequest updateLeave(LeaveRequest leaveRequest) {
        Optional<LeaveRequest> optional = leaveRepo.findById(leaveRequest.getLeaveRequestId());
        if (optional.isPresent()) {
            // If the LeaveRequest exists, update it
            LeaveRequest existingLeaveRequest = optional.get();
            existingLeaveRequest.setLeaveType(leaveRequest.getLeaveType()); // Example of updating a field
            existingLeaveRequest.setStartDate(leaveRequest.getStartDate());
            existingLeaveRequest.setEndDate(leaveRequest.getEndDate());
            // Add any other fields that need to be updated

            return leaveRepo.save(existingLeaveRequest); // Save the updated leave request
        } else {
            // If the LeaveRequest doesn't exist, you might want to throw an exception or
            // handle it
            throw new RuntimeException("LeaveRequest not found with ID: " + leaveRequest.getLeaveRequestId());
        }
    }

    public Optional<LeaveRequest> findByEmployee(Long employeeId) {
        return leaveRepo.findByEmployee(employeeId);
    }

    public LeaveRequest updatestatus(Long id, LeaveRequest leaveRequest) {

        Optional<LeaveRequest> optionalLeaveRequest = leaveRepo.findById(id);

        if (optionalLeaveRequest.isPresent()) {
            LeaveRequest existingLeaveRequest = optionalLeaveRequest.get();
            existingLeaveRequest.setStatus(leaveRequest.getStatus());
            return leaveRepo.save(existingLeaveRequest);
        }
        // Return a 404 Not Found response if the leave request doesn't exist
        return null;
    }

}
