package com.adarsh.LeaveManagementSystem.service;

import com.adarsh.LeaveManagementSystem.dao.LeaveDao;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequestStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveDao leaveDao;

    @InjectMocks
    private LeaveService leaveService;

    private LeaveRequest createTestLeaveRequest(Long id, Long employeeId, LeaveRequestStatus status) {
        LeaveRequest request = new LeaveRequest();
        request.setLeaveRequestId(id);
        request.setEmployeeid(employeeId);
        request.setLeaveType("VACATION");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(5));
        request.setNumberOfDays(5);
        request.setStatus(status);
        request.setReason("Family vacation");
        request.setRequestedAt(LocalDateTime.now());
        return request;
    }

    @Test
    void requestleaveOrsaveleave_ShouldSaveAndReturnLeaveRequest() {
        // Arrange
        LeaveRequest newRequest = createTestLeaveRequest(null, 1L, LeaveRequestStatus.PENDING);
        LeaveRequest savedRequest = createTestLeaveRequest(1L, 1L, LeaveRequestStatus.PENDING);
        
        when(leaveDao.requestleaveOrsaveleave(newRequest)).thenReturn(savedRequest);

        // Act
        LeaveRequest result = leaveService.requestleaveOrsaveleave(newRequest);

        // Assert
        assertNotNull(result.getLeaveRequestId());
        assertEquals(savedRequest, result);
        verify(leaveDao, times(1)).requestleaveOrsaveleave(newRequest);
    }

    @Test
    void getallleave_ShouldReturnAllLeaveRequests() {
        // Arrange
        List<LeaveRequest> mockRequests = List.of(
            createTestLeaveRequest(1L, 1L, LeaveRequestStatus.PENDING),
            createTestLeaveRequest(2L, 2L, LeaveRequestStatus.APPROVED)
        );
        when(leaveDao.getallleave()).thenReturn(mockRequests);

        // Act
        List<LeaveRequest> result = leaveService.getallleave();

        // Assert
        assertEquals(2, result.size());
        assertEquals(mockRequests, result);
        verify(leaveDao, times(1)).getallleave();
    }

    @Test
    void updateleave_ShouldUpdateAndReturnLeaveRequest() {
        // Arrange
        Long requestId = 1L;
        LeaveRequest existingRequest = createTestLeaveRequest(requestId, 1L, LeaveRequestStatus.PENDING);
        
        // Simulate the update
        LeaveRequest updatedRequest = createTestLeaveRequest(requestId, 1L, LeaveRequestStatus.APPROVED);
        updatedRequest.setApprovedAt(LocalDateTime.now());
        
        when(leaveDao.updateLeave(updatedRequest)).thenReturn(updatedRequest);
    
        // Act
        LeaveRequest result = leaveService.updateleave(updatedRequest);
    
        // Assert
        assertEquals(LeaveRequestStatus.APPROVED, result.getStatus());
        assertNotNull(result.getApprovedAt());
        assertNotEquals(existingRequest.getStatus(), result.getStatus()); // Verify status changed
        verify(leaveDao, times(1)).updateLeave(updatedRequest);
    }
    @Test
    void findByEmployee_WhenEmployeeExists_ShouldReturnLeaveRequests() {
        // Arrange
        Long employeeId = 1L;
        LeaveRequest mockRequest = createTestLeaveRequest(1L, employeeId, LeaveRequestStatus.PENDING);
        
        when(leaveDao.findByEmployee(employeeId)).thenReturn(Optional.of(mockRequest));

        // Act
        Optional<LeaveRequest> result = leaveService.findByEmployee(employeeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(employeeId, result.get().getEmployeeid());
       System.out.println(verify(leaveDao, times(1)).findByEmployee(employeeId));
        System.out.println("findByEmployee_WhenEmployeeExists_ShouldReturnLeaveRequests 1111111");
    }

    @Test
    void findByEmployee_WhenEmployeeNotExists_ShouldReturnEmpty() {
        // Arrange
        Long employeeId = 99L;
        when(leaveDao.findByEmployee(employeeId)).thenReturn(Optional.empty());

        // Act
        Optional<LeaveRequest> result = leaveService.findByEmployee(employeeId);

        // Assert
        assertFalse(result.isPresent());
        verify(leaveDao, times(1)).findByEmployee(employeeId);
    }

    @Test
    void updatestatus_ShouldUpdateStatusAndReturnLeaveRequest() {
        // Arrange
        Long requestId = 1L;
        LeaveRequest statusUpdate = createTestLeaveRequest(requestId, 1L, LeaveRequestStatus.APPROVED);
        statusUpdate.setApprovedAt(LocalDateTime.now());
        
        when(leaveDao.updatestatus(requestId, statusUpdate)).thenReturn(statusUpdate);

        // Act
        LeaveRequest result = leaveService.updatestatus(requestId, statusUpdate);

        // Assert
        assertEquals(LeaveRequestStatus.APPROVED, result.getStatus());
        assertNotNull(result.getApprovedAt());
        verify(leaveDao, times(1)).updatestatus(requestId, statusUpdate);
    }
}