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

    @CachePut(value = "leaves", key = "#leaverequest.id")
    public LeaveRequest requestleaveOrsaveleave(LeaveRequest leaverequest) {
        return dao.requestleaveOrsaveleave(leaverequest);
    }

    @Cacheable(value = "allleaves")
    public List<LeaveRequest> getallleave() {
        return dao.getallleave();
    }

    @CachePut(value = "leaves", key = "#leaverequest.id")
    public LeaveRequest updateleave(LeaveRequest leaverequest) {
        return dao.updateLeave(leaverequest);
    }

    @Cacheable(value = "leaveByEmployee", key = "#employeeId")
    public Optional<LeaveRequest> findByEmployee(Long employeeId) {
        return dao.findByEmployee(employeeId);
    }

    @CachePut(value = "leaves", key = "#id")
    public LeaveRequest updatestatus(Long id, LeaveRequest leaveRequest) {
        return dao.updatestatus(id, leaveRequest);
    }

    @CacheEvict(value = "leaves", key = "#id")
    public void deleteLeave(Long id) {
        if (id >0) {
                dao.deleteLeave(id); // Ensure this method exists in DAO
   
        }else{
            throw new NotFoundException(" the id you are trying is not found to delete");
        }
    
    }
}
