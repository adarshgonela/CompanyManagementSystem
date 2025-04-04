package com.adarsh.projectmanagement_service.controller;

import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import com.adarsh.projectmanagement_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

@PostMapping
    public Project createproject(Project project) {
        return projectService.createproject(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Project updatedProject = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjectsByName(@RequestParam String name) {
        List<Project> projects = projectService.searchProjectsByName(name);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<Project> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }

    // Employee management endpoints
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/{projectId}/employees/{empid}")
    public ResponseEntity<Project> addEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long empid) {
        Project project = projectService.addEmployeeToProject(projectId, empid);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectId}/employees/{empid}")
    public ResponseEntity<Project> removeEmployeeFromProject(
            @PathVariable Long projectId,
            @PathVariable Long empid) {
        Project project = projectService.removeEmployeeFromProject(projectId, empid);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}/employees")
    public ResponseEntity<List<Long>> getEmployeesForProject(
            @PathVariable Long projectId) {
        List<Long> employees = projectService.getEmployeesForProject(projectId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{empid}")
    public ResponseEntity<List<Project>> getProjectsForEmployee(
            @PathVariable Long empid) {
        List<Project> projects = projectService.getProjectsForEmployee(empid);
        return ResponseEntity.ok(projects);
    }

}
