package com.adarsh.projectmanagement_service.service;

import com.adarsh.projectmanagement_service.dao.Projectdao;
import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private Projectdao projectdao;

    public Project createproject(Project project) {

    return projectdao.createproject(project);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectdao.getAllProjects();
    }

    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectdao.getProjectById(id);
    }
    @Transactional(readOnly = true)

    public Project updateProject(Long id, Project projectDetails) {
        return projectdao.updateProject(id,projectDetails);
    }
    @Transactional(readOnly = true)

    public void deleteProject(Long id) {
         projectdao.deleteProject(id);
    }
    @Transactional(readOnly = true)

    public List<Project> searchProjectsByName(String name) {
        return projectdao.searchProjectsByName(name);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectdao.getProjectsByStatus(status);
    }


    // Employee management endpoints
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Project addEmployeeToProject(Long projectId, Long empid) {
        return projectdao.addEmployeeToProject(projectId,empid);
    }

    public Project removeEmployeeFromProject(Long projectId, Long empid) {
   return projectdao.removeEmployeeFromProject(projectId,empid);
    }
    @Transactional(readOnly = true)
    public List<Long> getEmployeesForProject(Long projectId) {
        return projectdao.getEmployeesForProject(projectId);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsForEmployee(Long empid) {
        return projectdao.getProjectsForEmployee(empid);
    }
}
