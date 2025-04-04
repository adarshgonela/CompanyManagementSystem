package com.adarsh.projectmanagement_service.dao;

import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.repo.Projectrepo;

import java.util.List;
import java.util.Optional;

@Repository
public class Projectdao {

    @Autowired
    private Projectrepo  projectrepo;

public Project createproject(Project project){
    return projectrepo.save(project);

}

    public List<Project> getAllProjects() {
        return projectrepo.findAll();
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectrepo.findById(id);
        return project.orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = getProjectById(id);
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setStatus(projectDetails.getStatus());
        return projectrepo.save(project);
    }

    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        projectrepo.delete(project);
    }

    public List<Project> searchProjectsByName(String name) {
        return projectrepo.findByNameContainingIgnoreCase(name);
    }

    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectrepo.findByStatus(status);
    }


    // Employee management endpoints
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Project addEmployeeToProject(Long projectId, Long empid) {
        Project project = getProjectById(projectId);
        project.addEmployeeId(empid);
        return projectrepo.save(project);
    }

    public Project removeEmployeeFromProject(Long projectId, Long empid) {
        Project project = getProjectById(projectId);
        project.removeEmployeeId(empid);
        return projectrepo.save(project);
}

    public List<Long> getEmployeesForProject(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getEmployeeIds();
    }

    public List<Project> getProjectsForEmployee(Long empid) {
        return projectrepo.findByEmployeeId(empid);
    }
}
