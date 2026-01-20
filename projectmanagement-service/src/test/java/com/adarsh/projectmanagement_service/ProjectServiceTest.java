package com.adarsh.projectmanagement_service;

import com.adarsh.projectmanagement_service.Exceptions.ProjectNotFoundException;
import com.adarsh.projectmanagement_service.dao.Projectdao;
import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import com.adarsh.projectmanagement_service.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private Projectdao projectdao;

    @InjectMocks
    private ProjectService projectService;

    private Project mockProject;
    private static final Long PROJECT_ID = 1L;
    private static final Long EMPLOYEE_ID = 101L;
    private static final String PROJECT_NAME = "AI Platform";

    @BeforeEach
    void setUp() {
        mockProject = new Project();
        mockProject.setId(PROJECT_ID);
        mockProject.setName(PROJECT_NAME);
        mockProject.setDescription("Build GPT App");
        mockProject.setStatus(ProjectStatus.IN_PROGRESS);
    }

    @Test
    void testCreateProject_WhenNotExists_ShouldCreateSuccessfully() {
        // Arrange
        when(projectdao.getdatabyprojectname(PROJECT_NAME)).thenReturn(Collections.emptyList());
        when(projectdao.createproject(mockProject)).thenReturn(mockProject);

        // Act
        Project created = projectService.createproject(mockProject);

        // Assert
        assertNotNull(created);
        assertEquals(PROJECT_NAME, created.getName());
        assertEquals(ProjectStatus.IN_PROGRESS, created.getStatus());
        verify(projectdao, times(1)).getdatabyprojectname(PROJECT_NAME);
        verify(projectdao, times(1)).createproject(mockProject);
    }

    @Test
    void testCreateProject_WhenExists_ShouldThrowProjectNotFoundException() {
        // Arrange
        when(projectdao.getdatabyprojectname(PROJECT_NAME)).thenReturn(List.of(mockProject));

        // Act & Assert
        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, 
            () -> projectService.createproject(mockProject));

        assertEquals("the project you are entering is already present", exception.getMessage());
        verify(projectdao, times(1)).getdatabyprojectname(PROJECT_NAME);
        verify(projectdao, never()).createproject(any(Project.class));
    }

    @Test
    void testGetAllProjects_WhenProjectsExist_ShouldReturnProjectList() {
        // Arrange
        List<Project> expectedProjects = List.of(mockProject);
        when(projectdao.getAllProjects()).thenReturn(expectedProjects);

        // Act
        List<Project> result = projectService.getAllProjects();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProject, result.get(0));
        verify(projectdao, times(1)).getAllProjects();
    }

    @Test
    void testGetAllProjects_WhenNoProjects_ShouldReturnEmptyList() {
        // Arrange
        when(projectdao.getAllProjects()).thenReturn(Collections.emptyList());

        // Act
        List<Project> result = projectService.getAllProjects();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectdao, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById_WhenProjectExists_ShouldReturnProject() {
        // Arrange
        when(projectdao.getProjectById(PROJECT_ID)).thenReturn(mockProject);

        // Act
        Project result = projectService.getProjectById(PROJECT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(PROJECT_NAME, result.getName());
        assertEquals(PROJECT_ID, result.getId());
        verify(projectdao, times(1)).getProjectById(PROJECT_ID);
    }

    @Test
    void testGetProjectById_WhenProjectNotExists_ShouldReturnNull() {
        // Arrange
        when(projectdao.getProjectById(PROJECT_ID)).thenReturn(null);

        // Act
        Project result = projectService.getProjectById(PROJECT_ID);

        // Assert
        assertNull(result);
        verify(projectdao, times(1)).getProjectById(PROJECT_ID);
    }

    @Test
    void testUpdateProject_ShouldReturnUpdatedProject() {
        // Arrange
        Project updatedProject = new Project();
        updatedProject.setId(PROJECT_ID);
        updatedProject.setName("AI Updated");
        updatedProject.setDescription("Updated Description");
        updatedProject.setStatus(ProjectStatus.COMPLETED);

        when(projectdao.updateProject(PROJECT_ID, updatedProject)).thenReturn(updatedProject);

        // Act
        Project result = projectService.updateProject(PROJECT_ID, updatedProject);

        // Assert
        assertNotNull(result);
        assertEquals("AI Updated", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(ProjectStatus.COMPLETED, result.getStatus());
        verify(projectdao, times(1)).updateProject(PROJECT_ID, updatedProject);
    }

    @Test
    void testAddEmployeeToProject_ShouldReturnProject() {
        // Arrange
        when(projectdao.addEmployeeToProject(PROJECT_ID, EMPLOYEE_ID)).thenReturn(mockProject);

        // Act
        Project result = projectService.addEmployeeToProject(PROJECT_ID, EMPLOYEE_ID);

        // Assert
        assertNotNull(result);
        assertEquals(mockProject, result);
        verify(projectdao, times(1)).addEmployeeToProject(PROJECT_ID, EMPLOYEE_ID);
    }

    @Test
    void testDeleteProject_ShouldCallDaoMethod() {
        // Arrange
        doNothing().when(projectdao).deleteProject(PROJECT_ID);

        // Act
        projectService.deleteProject(PROJECT_ID);

        // Assert
        verify(projectdao, times(1)).deleteProject(PROJECT_ID);
    }
}