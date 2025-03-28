package com.adarsh.LeaveManagementSystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ConfigurationProperties(prefix = "leave")
public class LeaveConfig {

    @Value("${leave.vacationCount}")
    private int vacationCount;

    @Value("${leave.sickLeaveCount}")
    private int sickLeaveCount;

    @Value("${leave.remoteWorkCount}")
    private int remoteWorkCount;

    @Value("${leave.personalCount}")
    private int personalCount;

    @Value("${leave.paidCount}")
    private int paidCount;

    // Getters for the leave counts
    public int getVacationCount() {
        return vacationCount;
    }

    public int getSickLeaveCount() {
        return sickLeaveCount;
    }

    public int getRemoteWorkCount() {
        return remoteWorkCount;
    }

    public int getPersonalCount() {
        return personalCount;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(int paidCount) {
        this.paidCount = paidCount;
    }
}