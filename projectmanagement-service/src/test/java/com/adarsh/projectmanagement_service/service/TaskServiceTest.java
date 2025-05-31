package com.adarsh.projectmanagement_service.service;

import com.adarsh.projectmanagement_service.Exceptions.EmployeeNotFound;
import com.adarsh.projectmanagement_service.clientmethods.FeignclientmethodsEMS;
import com.adarsh.projectmanagement_service.dao.TaskDao;
import com.adarsh.projectmanagement_service.dto.EmployeeClient;
import com.adarsh.projectmanagement_service.dto.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskDao taskDao;

    @Mock
    private FeignclientmethodsEMS feignclientmethodsEMS;

    @InjectMocks
    private TaskService taskService;

    private Task createTestTask(Long id, String title, String status, Long empId) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setStatus(status);
        task.setEmpId(empId);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return task;
    }

   @Test
void createTask_WhenEmployeeExists_ShouldCreateTask() {
    // Arrange
    Long empId = 1L;
    Task newTask = createTestTask(null, "New Task", "PENDING", empId);
    Task savedTask = createTestTask(1L, "New Task", "PENDING", empId);
    
    // Create a proper mock Employee object
    EmployeeClient mockEmployee = new EmployeeClient();
    mockEmployee.setEmpid(empId);
    mockEmployee.setFirstName("Test Employee");
mockEmployee.setLastName("lastname");
    when(feignclientmethodsEMS.getEmployeeByIdpost(empId))
            .thenReturn(Optional.of(mockEmployee)); // Use proper Employee object
    when(taskDao.createTask(any(Task.class))).thenReturn(savedTask);

    // Act
    Task result = taskService.createTask(newTask);

    // Assert
    assertNotNull(result.getId(), "Task ID should be generated");
    assertEquals("New Task", result.getTitle(), "Title should match");
    assertEquals("PENDING", result.getStatus(), "Default status should be PENDING");
    assertEquals(empId, result.getEmpId(), "Employee ID should match");
    assertNotNull(result.getCreatedAt(), "Created timestamp should be set");
    assertNotNull(result.getUpdatedAt(), "Updated timestamp should be set");
    
    verify(feignclientmethodsEMS, times(1)).getEmployeeByIdpost(empId);
    verify(taskDao, times(1)).createTask(any(Task.class));
}

    @Test
    void createTask_WhenEmployeeNotExists_ShouldThrowException() {
        // Arrange
        Long empId = 99L;
        Task newTask = createTestTask(null, "New Task", "PENDING", empId);

        when(feignclientmethodsEMS.getEmployeeByIdpost(empId))
                .thenReturn(Optional.empty()); // Mock employee doesn't exist

        // Act & Assert
        assertThrows(EmployeeNotFound.class, () -> taskService.createTask(newTask));
        verify(feignclientmethodsEMS, times(1)).getEmployeeByIdpost(empId);
        verify(taskDao, never()).createTask(any());
    }

    @Test
    void tasksUpdate_ShouldUpdateAndReturnTask() {
        // Arrange
        Long taskId = 1L;
        Task updatedTask = createTestTask(taskId, "Updated Task", "IN_PROGRESS", 1L);

        when(taskDao.updateTask(taskId, updatedTask)).thenReturn(updatedTask);

        // Act
        Task result = taskService.tasksUpdate(taskId, updatedTask);

        // Assert
        assertEquals("Updated Task", result.getTitle());
        assertEquals("IN_PROGRESS", result.getStatus());
        verify(taskDao, times(1)).updateTask(taskId, updatedTask);
    }

    @Test
    void getTaskUsingID_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        Task expectedTask = createTestTask(taskId, "Test Task", "PENDING", 1L);

        when(taskDao.getTaskById(taskId)).thenReturn(expectedTask);

        // Act
        Task result = taskService.getTaskUsingID(taskId);

        // Assert
        assertEquals(expectedTask, result);
        verify(taskDao, times(1)).getTaskById(taskId);
    }

    @Test
    void deleteTask_ShouldCallDaoDelete() {
        // Arrange
        Long taskId = 1L;
        doNothing().when(taskDao).deleteTask(taskId);

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskDao, times(1)).deleteTask(taskId);
    }

    @Test
    void getalltasks_ShouldReturnAllTasks() {
        // Arrange
        List<Task> mockTasks = List.of(
                createTestTask(1L, "Task 1", "PENDING", 1L),
                createTestTask(2L, "Task 2", "COMPLETED", 2L)
        );
        when(taskDao.getAllTasks()).thenReturn(mockTasks);

        // Act
        List<Task> result = taskService.getalltasks();

        // Assert
        assertEquals(2, result.size());
        assertEquals(mockTasks, result);
        verify(taskDao, times(1)).getAllTasks();
    }
}