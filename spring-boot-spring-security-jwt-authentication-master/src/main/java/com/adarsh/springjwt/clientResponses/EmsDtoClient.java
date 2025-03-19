package com.adarsh.springjwt.clientResponses;

import java.time.LocalDate;

public class EmsDtoClient {
    private Long id;
    private Long empid;

    private String firstName;
    private String lastName;
    // private String email;
    private String phone;
    private String department;
    private String position;
    private LocalDate hireDate;
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
    public Long getEmpid() {
        return empid;
    }
    public void setEmpid(Long empid) {
        this.empid = empid;
    }


}
