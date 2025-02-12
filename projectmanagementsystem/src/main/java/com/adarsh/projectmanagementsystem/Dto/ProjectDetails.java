package com.adarsh.projectmanagementsystem.Dto;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

@Entity
public class ProjectDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String projectName;
    private String projectManager;
    private String startDate;
    private String endDate;
    private String status;
    private double budget;
    private String resources;
    private String dependencies;
    private String projectClosure;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employee;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectManager() {
        return projectManager;
    }
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public String getResources() {
        return resources;
    }
    public void setResources(String resources) {
        this.resources = resources;
    }
    public String getDependencies() {
        return dependencies;
    }
    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }
    public String getProjectClosure() {
        return projectClosure;
    }
    public void setProjectClosure(String projectClosure) {
        this.projectClosure = projectClosure;
    }
    public List<Employee> getEmployee() {
        return employee;
    }
    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }
    
    

    
    

    
    
}
