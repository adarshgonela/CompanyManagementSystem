package com.adarsh.LeaveManagementSystem.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int vacationCount;

    private int sickLeaveCount;

    private int remoteWorkCount;

    private int personalCount;

    private int unpaidCount;
    private Long empid;

    // private Long empid;
    //
    // @Value("${leave.vacationCount}")
    // private int vacationCount;
    //
    // @Value("${leave.sickLeaveCount}")
    // private int sickLeaveCount;
    //
    // @Value("${leave.remoteWorkCount}")
    // private int remoteWorkCount;
    //
    // @Value("${leave.personalCount}")
    // private int personalCount;
    //
    // @Value("${leave.unpaidCount}")
    // private int unpaidCount;

    public LeaveType(Long empid) {
    }

    public LeaveType() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVacationCount() {
        return vacationCount;
    }

    public void setVacationCount(int vacationCount) {
        this.vacationCount = vacationCount;
    }

    public int getSickLeaveCount() {
        return sickLeaveCount;
    }

    public void setSickLeaveCount(int sickLeaveCount) {
        this.sickLeaveCount = sickLeaveCount;
    }

    public int getRemoteWorkCount() {
        return remoteWorkCount;
    }

    public void setRemoteWorkCount(int remoteWorkCount) {
        this.remoteWorkCount = remoteWorkCount;
    }

    public int getPersonalCount() {
        return personalCount;
    }

    public void setPersonalCount(int personalCount) {
        this.personalCount = personalCount;
    }

    public int getUnpaidCount() {
        return unpaidCount;
    }

    public void setUnpaidCount(int unpaidCount) {
        this.unpaidCount = unpaidCount;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }
}
