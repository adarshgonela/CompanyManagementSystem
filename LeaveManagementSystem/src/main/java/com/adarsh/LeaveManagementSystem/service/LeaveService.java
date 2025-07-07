package com.adarsh.LeaveManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.adarsh.LeaveManagementSystem.dao.LeaveDao;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;

import jakarta.ws.rs.NotFoundException;

@Service
public class LeaveService {

    @Autowired
    private LeaveDao dao;

    // Save or create a leave request
    @CachePut(value = "leaves", key = "#leaverequest.leaveRequestId")
    public LeaveRequest requestleaveOrsaveleave(LeaveRequest leaverequest) {
        return dao.requestleaveOrsaveleave(leaverequest);
    }

    // Get all leave requests
    @Cacheable(value = "allleaves")
    public List<LeaveRequest> getallleave() {
        List<LeaveRequest> leaves = dao.getallleave();
        leaves.forEach(leave -> System.out.println("Leave Request: " + leave));
        return leaves;
    }

    // Update a leave request
    @CachePut(value = "leaves", key = "#leaverequest.leaveRequestId")
    public LeaveRequest updateleave(LeaveRequest leaverequest) {
        if (leaverequest.getLeaveRequestId() == null) {
            throw new NotFoundException("Leave request ID is required for update");
        }

        return dao.updateLeave(leaverequest);
    }

    // Find leave by employee ID
    @Cacheable(value = "leaveByEmployee", key = "#employeeId")
    public Optional<LeaveRequest> findByEmployee(Long employeeId) {
        Optional<LeaveRequest> employees = dao.findByEmployee(employeeId);
        employees.ifPresentOrElse(
            emp -> System.out.println("Leave request found: " + emp),
            () -> System.err.println("Leave request for employee ID " + employeeId + " not found")
        );
        return employees;
    }

    // Update leave status
    @CachePut(value = "leaves", key = "#id")
    public LeaveRequest updatestatus(Long id, LeaveRequest leaveRequest) {
        return dao.updatestatus(id, leaveRequest);
    }

    // Delete leave by ID
    @CacheEvict(value = "leaves", key = "#id")
    public void deleteLeave(Long id) {
        if (id > 0) {
            dao.deleteLeave(id); // Ensure this method exists in DAO
        } else {
            throw new NotFoundException("The ID you are trying to delete is not found.");
        }
    }

    // Paginated leave fetch
    @Cacheable(value = "leavePages", key = "'leavePage_' + #pageNumber + '_' + #pageSize")
    public List<LeaveRequest> getPaginatedLeaves(int pageNumber, int pageSize) {
        List<LeaveRequest> leaves = dao.getPaginatedLeaves(pageNumber, pageSize);

        if (leaves.isEmpty()) {
            System.out.println("No leave requests found for page " + pageNumber);
        } else {
            System.out.println("Leave requests on page " + pageNumber + ": " + leaves.size());
        }

        return leaves;
    }
}
