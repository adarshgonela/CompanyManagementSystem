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

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    
    @Mock
    private Projectdao projectdao;

    @InjectMocks
    private ProjectService projectService;

    private Project mockProject;

    @BeforeEach
    void setUp() {
        mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setName("AI Platform");
        mockProject.setDescription("Build GPT App");
        mockProject.setStatus(ProjectStatus.IN_PROGRESS);
    }

    @Test
    void testCreateProject_WhenNotExists_ShouldCreate() {
        when(projectdao.getdatabyprojectname("AI Platform")).thenReturn(List.of());
        when(projectdao.createproject(mockProject)).thenReturn(mockProject);

        Project created = projectService.createproject(mockProject);

        assertNotNull(created);
        assertEquals("AI Platform", created.getName());
        verify(projectdao, times(1)).createproject(mockProject);
    }

    @Test
    void testCreateProject_WhenExists_ShouldThrowException() {
        when(projectdao.getdatabyprojectname("AI Platform")).thenReturn(List.of(mockProject));

        Exception ex = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.createproject(mockProject);
        });

        assertEquals("the project you are entering is already present", ex.getMessage());
        verify(projectdao, never()).createproject(mockProject);
    }

    @Test
    void testGetAllProjects() {
        when(projectdao.getAllProjects()).thenReturn(List.of(mockProject));

        List<Project> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        verify(projectdao, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById() {
        when(projectdao.getProjectById(1L)).thenReturn(mockProject);

        Project result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("AI Platform", result.getName());
    }

    @Test
    void testUpdateProject() {
        Project updatedProject = new Project();
        updatedProject.setId(1L);
        updatedProject.setName("AI Updated");
        updatedProject.setDescription("Updated Description");
        updatedProject.setStatus(ProjectStatus.COMPLETED);

        when(projectdao.updateProject(1L, updatedProject)).thenReturn(updatedProject);

        Project result = projectService.updateProject(1L, updatedProject);

        assertEquals("AI Updated", result.getName());
        assertEquals(ProjectStatus.COMPLETED, result.getStatus());
    }

    @Test
    void testAddEmployeeToProject() {
        when(projectdao.addEmployeeToProject(1L, 101L)).thenReturn(mockProject);

        Project result = projectService.addEmployeeToProject(1L, 101L);

        assertNotNull(result);
        verify(projectdao).addEmployeeToProject(1L, 101L);
    }

}
