package com.adarsh.projectmanagement_service.service;

import com.adarsh.projectmanagement_service.Exceptions.ProjectNotFoundException;
import com.adarsh.projectmanagement_service.dao.Projectdao;
import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private Projectdao projectdao;

    @CachePut(value = "projects", key = "#result.id")
    public Project createproject(Project project) {

        List<Project> optional = projectdao.getdatabyprojectname(project.getName());
        if (optional.isEmpty()) {
            return projectdao.createproject(project);
        }
        throw new ProjectNotFoundException("the project you are entering is already present");
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "allProjects")
    public List<Project> getAllProjects() {
        return projectdao.getAllProjects();
        
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "projects", key = "#id")
    public Project getProjectById(Long id) {
        return projectdao.getProjectById(id);
    }

    @Transactional(readOnly = true)
    @CachePut(value = "projects", key = "#id")
    public Project updateProject(Long id, Project projectDetails) {
        return projectdao.updateProject(id, projectDetails);
    }

    @Transactional(readOnly = true)
    @CacheEvict(value = "projects", key = "#id")
    public void deleteProject(Long id) {
        projectdao.deleteProject(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "projectsByName", key = "#name")
    public List<Project> searchProjectsByName(String name) {
        return projectdao.searchProjectsByName(name);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "projectsByStatus", key = "#status")
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectdao.getProjectsByStatus(status);
    }

    public List<Project> getdatabyprojectname(String name) {
        return projectdao.getdatabyprojectname(name);
    }

    @CachePut(value = "projects", key = "#projectId")
    public Project updateEmployeeId(Long projectId, Long employeeId) {
        return projectdao.updateEmployeeId(projectId, employeeId);
    }

    @CachePut(value = "projects", key = "#projectId")
    public Project removeEmployeeIdfromProject(Long projectId, Long employeeId) {
        return projectdao.removeEmployeeFromProject(projectId, employeeId);
    }

    // Employee management endpoints
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @CachePut(value = "projects", key = "#projectId")
    public Project addEmployeeToProject(Long projectId, Long empid) {
        return projectdao.addEmployeeToProject(projectId, empid);
    }

    @CachePut(value = "projects", key = "#projectId")
    public Project removeEmployeeFromProject(Long projectId, Long empid) {
        return projectdao.removeEmployeeFromProject(projectId, empid);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "employeesForProject", key = "#projectId")
    public List<Long> getEmployeesForProject(Long projectId) {
        return projectdao.getEmployeesForProject(projectId);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "projectsForEmployee", key = "#empid")
    public List<Project> getProjectsForEmployee(Long empid) {
        return projectdao.getProjectsForEmployee(empid);
    }

}
