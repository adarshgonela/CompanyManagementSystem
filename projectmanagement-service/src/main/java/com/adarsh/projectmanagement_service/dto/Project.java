package com.adarsh.projectmanagement_service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.NOT_STARTED;

    @ElementCollection
    @CollectionTable(name = "project_employees", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "employee_id")
    private List<Long> employeeIds = new ArrayList<>();

    public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Helper methods
    public void addEmployeeId(Long empid) {
        if (!this.employeeIds.contains(empid)) {
            this.employeeIds.add(empid);
        }
    }

    public void removeEmployeeId(Long empid) {
        this.employeeIds.remove(empid);
    }
}

enum ProjectStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    ON_HOLD,
    CANCELLED
}