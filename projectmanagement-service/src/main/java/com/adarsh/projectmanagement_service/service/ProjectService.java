package com.adarsh.projectmanagement_service.service;

import com.adarsh.projectmanagement_service.Exceptions.ProjectNotFoundException;
import com.adarsh.projectmanagement_service.dao.Projectdao;
import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "projects")
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final Projectdao projectdao;

    public ProjectService(Projectdao projectdao) {
        this.projectdao = projectdao;
    }

    // CREATE PROJECT
    @Transactional
    @Caching(
        put = {@CachePut(key = "#result.id")},
        evict = {
            @CacheEvict(value = {"allProjects", "projectsByName", "projectsByStatus"}, allEntries = true)
        }
    )
    public Project createProject(Project project) {

        validateProject(project);

        List<Project> existing = projectdao.getdatabyprojectname(project.getName());

        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("Project with given name already exists");
        }

        Project saved = projectdao.createproject(project);
        logger.info("Project created with ID: {}", saved.getId());

        return saved;
    }

    // GET ALL
    @Transactional(readOnly = true)
    @Cacheable(value = "allProjects")
    public List<Project> getAllProjects() {
        List<Project> projects = projectdao.getAllProjects();
        logger.info("Fetched {} projects", projects.size());
        return projects;
    }

    // GET BY ID
    @Transactional(readOnly = true)
    @Cacheable(key = "#id")
    public Project getProjectById(Long id) {

        validateId(id);

        Project project = projectdao.getProjectById(id);

        if (project == null) {
            throw new ProjectNotFoundException("Project not found with ID: " + id);
        }

        return project;
    }

    // UPDATE
    @Transactional
    @Caching(
        put = {@CachePut(key = "#id")},
        evict = {
            @CacheEvict(value = {"allProjects", "projectsByName", "projectsByStatus"}, allEntries = true)
        }
    )
    public Project updateProject(Long id, Project projectDetails) {

        validateId(id);
        validateProject(projectDetails);

        Project updated = projectdao.updateProject(id, projectDetails);

        if (updated == null) {
            throw new ProjectNotFoundException("Project not found with ID: " + id);
        }

        logger.info("Updated project with ID: {}", id);
        return updated;
    }

    // DELETE
    @Transactional
    @Caching(evict = {
        @CacheEvict(key = "#id"),
        @CacheEvict(value = {"allProjects", "projectsByName", "projectsByStatus"}, allEntries = true)
    })
    public void deleteProject(Long id) {

        validateId(id);

        projectdao.deleteProject(id);
        logger.info("Deleted project with ID: {}", id);
    }

    // SEARCH BY NAME
    @Transactional(readOnly = true)
    @Cacheable(value = "projectsByName", key = "#name")
    public List<Project> searchProjectsByName(String name) {

        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }

        return projectdao.searchProjectsByName(name);
    }

    // FILTER BY STATUS
    @Transactional(readOnly = true)
    @Cacheable(value = "projectsByStatus", key = "#status")
    public List<Project> getProjectsByStatus(ProjectStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("Project status cannot be null");
        }

        return projectdao.getProjectsByStatus(status);
    }

    // ASSIGN EMPLOYEE
    @Transactional
    @CachePut(key = "#projectId")
    @CacheEvict(value = {"employeesForProject", "projectsForEmployee"}, allEntries = true)
    public Project addEmployeeToProject(Long projectId, Long empId) {

        validateId(projectId);
        validateId(empId);

        Project updated = projectdao.addEmployeeToProject(projectId, empId);
        logger.info("Added employee {} to project {}", empId, projectId);

        return updated;
    }

    // REMOVE EMPLOYEE
    @Transactional
    @CachePut(key = "#projectId")
    @CacheEvict(value = {"employeesForProject", "projectsForEmployee"}, allEntries = true)
    public Project removeEmployeeFromProject(Long projectId, Long empId) {

        validateId(projectId);
        validateId(empId);

        Project updated = projectdao.removeEmployeeFromProject(projectId, empId);
        logger.info("Removed employee {} from project {}", empId, projectId);

        return updated;
    }

    // GET EMPLOYEES FOR PROJECT
    @Transactional(readOnly = true)
    @Cacheable(value = "employeesForProject", key = "#projectId")
    public List<Long> getEmployeesForProject(Long projectId) {

        validateId(projectId);

        return projectdao.getEmployeesForProject(projectId);
    }

    // GET PROJECTS FOR EMPLOYEE
    @Transactional(readOnly = true)
    @Cacheable(value = "projectsForEmployee", key = "#empId")
    public List<Project> getProjectsForEmployee(Long empId) {

        validateId(empId);

        return projectdao.getProjectsForEmployee(empId);
    }

    // =========================
    // VALIDATION METHODS
    // =========================

    private void validateProject(Project project) {
        if (ObjectUtils.isEmpty(project) || ObjectUtils.isEmpty(project.getName())) {
            throw new IllegalArgumentException("Project or project name cannot be null");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}