package com.adarsh.LeaveManagementSystem.dto;

import com.adarsh.LeaveManagementSystem.refDto.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "leave")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id")
    private Long leaveRequestId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "leave_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private String leaveType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "number_of_days", nullable = false)
    private Double numberOfDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private String status ;  // Default status is Pending

    @Column(name = "reason")
    private String reason;

    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @ManyToOne
    @JoinColumn(name = "approver_id") // Only one approver now
    private Employee approver; // Could be a manager or HR who approves the leave request

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private String approvalStatus;  // Approval status (e.g., 'Manager Approved', 'HR Approved')

    // Getters and setters
}

