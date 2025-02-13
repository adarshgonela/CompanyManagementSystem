package com.adarsh.projectmanagementsystem.Dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String position;
    private LocalDate hireDate;
    private String roletype;

    @ManyToOne
    @JoinColumn(name = "project_id") // foreign key column
    private ProjectDetails projectDetails;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> task;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    public String getRoletype() {
        return roletype;
    }
    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }
    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }
    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }
    public List<Task> getTask() {
        return task;
    }
    public void setTask(List<Task> task) {
        this.task = task;
    }

    
    
}
