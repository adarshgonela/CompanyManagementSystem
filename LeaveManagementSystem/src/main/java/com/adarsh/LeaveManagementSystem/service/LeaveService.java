package com.adarsh.LeaveManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.adarsh.LeaveManagementSystem.dao.LeaveDao;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;


@Service
@CacheConfig(cacheNames = "leaves")
public class LeaveService {

    private static final Logger logger = LoggerFactory.getLogger(LeaveService.class);

    private final LeaveDao dao;

    public LeaveService(LeaveDao dao) {
        this.dao = dao;
    }

    // Create or Save Leave
    @CachePut(key = "#result.leaveRequestId")
    @CacheEvict(value = {"allLeaves", "leavePages", "leaveByEmployee"}, allEntries = true)
    public LeaveRequest saveLeave(LeaveRequest leaveRequest) {
        validateLeaveRequest(leaveRequest);

        LeaveRequest saved = dao.requestleaveOrsaveleave(leaveRequest);
        logger.info("Leave saved with ID: {}", saved.getLeaveRequestId());

        return saved;
    }

    // Get All Leaves
    @Cacheable(value = "allLeaves")
    public List<LeaveRequest> getAllLeaves() {
        List<LeaveRequest> leaves = dao.getallleave();
        logger.info("Fetched {} leave requests", leaves.size());
        return leaves;
    }

    // Update Leave
    @CachePut(key = "#leaveRequest.leaveRequestId")
    @CacheEvict(value = {"allLeaves", "leavePages", "leaveByEmployee"}, allEntries = true)
    public LeaveRequest updateLeave(LeaveRequest leaveRequest) {

        if (leaveRequest.getLeaveRequestId() == null) {
            throw new IllegalArgumentException("Leave request ID must not be null for update");
        }

        LeaveRequest updated = dao.updateLeave(leaveRequest);
        logger.info("Updated leave with ID: {}", leaveRequest.getLeaveRequestId());

        return updated;
    }

    // Find by Employee ID
    @Cacheable(value = "leaveByEmployee", key = "#employeeId")
    public Optional<LeaveRequest> findByEmployee(Long employeeId) {

        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        Optional<LeaveRequest> leave = dao.findByEmployee(employeeId);

        if (leave.isPresent()) {
            logger.info("Leave found for employee ID: {}", employeeId);
        } else {
            logger.warn("No leave found for employee ID: {}", employeeId);
        }

        return leave;
    }

    // Update Leave Status
    @CachePut(key = "#id")
    @CacheEvict(value = {"allLeaves", "leavePages", "leaveByEmployee"}, allEntries = true)
    public LeaveRequest updateStatus(Long id, LeaveRequest leaveRequest) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid leave ID");
        }

        LeaveRequest updated = dao.updatestatus(id, leaveRequest);
        logger.info("Updated status for leave ID: {}", id);

        return updated;
    }

    // Delete Leave
    @Caching(evict = {
        @CacheEvict(key = "#id"),
        @CacheEvict(value = {"allLeaves", "leavePages", "leaveByEmployee"}, allEntries = true)
    })
    public void deleteLeave(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid leave ID");
        }

        dao.deleteLeave(id);
        logger.info("Deleted leave with ID: {}", id);
    }

    // Pagination
    @Cacheable(value = "leavePages", key = "'page_' + #pageNumber + '_' + #pageSize")
    public List<LeaveRequest> getPaginatedLeaves(int pageNumber, int pageSize) {

        if (pageNumber < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }

        List<LeaveRequest> leaves = dao.getPaginatedLeaves(pageNumber, pageSize);

        logger.info("Fetched {} leaves for page {} with size {}", 
                     leaves.size(), pageNumber, pageSize);

        return leaves;
    }

    // Validation Helper
    private void validateLeaveRequest(LeaveRequest leaveRequest) {
        if (ObjectUtils.isEmpty(leaveRequest)) {
            throw new IllegalArgumentException("Leave request cannot be null or empty");
        }
    }
}