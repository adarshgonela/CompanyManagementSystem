package com.adarsh.EmployeeManagementSystem.service;

import com.adarsh.EmployeeManagementSystem.dao.EmployeeDao;
import com.adarsh.EmployeeManagementSystem.dto.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDao dao;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee createTestEmployee(Long id, String firstName, String lastName, String department) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(department);
        employee.setPhone("1234567890");
        employee.setPosition("Developer");
        employee.setHireDate(LocalDate.now());
        return employee;
    }

    @Test
    void getEmployeeById_WhenEmployeeExists_ShouldReturnEmployee() {
        // Arrange
        Long employeeId = 1L;
        Employee mockEmployee = createTestEmployee(employeeId, "John", "Doe", "IT");
        when(dao.getEmployeeById(employeeId)).thenReturn(Optional.of(mockEmployee));

        // Act
        Optional<Employee> result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockEmployee, result.get());
        verify(dao, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void getEmployeeById_WhenEmployeeNotExists_ShouldReturnEmpty() {
        // Arrange
        Long employeeId = 1L;
        when(dao.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        // Act
        Optional<Employee> result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertFalse(result.isPresent());
        verify(dao, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        // Arrange
        Employee newEmployee = createTestEmployee(null, "Jane", "Smith", "HR");
        Employee savedEmployee = createTestEmployee(1L, "Jane", "Smith", "HR");
        when(dao.createEmployee(newEmployee)).thenReturn(savedEmployee);

        // Act
        Employee result = employeeService.createEmployee(newEmployee);

        // Assert
        assertNotNull(result.getId());
        assertEquals(savedEmployee, result);
        verify(dao, times(1)).createEmployee(newEmployee);
    }

    @Test
    void updateEmployee_WhenEmployeeExists_ShouldReturnUpdatedEmployee() {
        // Arrange
        Long employeeId = 1L;
        Employee updatedDetails = createTestEmployee(null, "New", "Name", "HR");
        Employee expectedUpdatedEmployee = createTestEmployee(employeeId, "New", "Name", "HR");
    
        when(dao.updateEmployee(updatedDetails, employeeId)).thenReturn(expectedUpdatedEmployee);
    
        // Act
        Employee result = employeeService.updateEmployee(updatedDetails, employeeId);
    
        // Assert
        assertEquals(employeeId, result.getId());
        assertEquals("New", result.getFirstName());
        assertEquals("Name", result.getLastName());
        assertEquals("HR", result.getDepartment());
        verify(dao, times(1)).updateEmployee(updatedDetails, employeeId);
    }

    @Test
    void deleteEmployee_ShouldCallDaoDelete() {
        // Arrange
        Long employeeId = 1L;
        doNothing().when(dao).deleteEmployee(employeeId);

        // Act
        employeeService.deleteEmployee(employeeId);

        // Assert
        verify(dao, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        // Arrange
        List<Employee> mockEmployees = Arrays.asList(
            createTestEmployee(1L, "John", "Doe", "IT"),
            createTestEmployee(2L, "Jane", "Smith", "HR")
        );
        when(dao.getAllEmployee()).thenReturn(mockEmployees);

        // Act
        List<Employee> result = employeeService.getAllEmployee();

        // Assert
        assertEquals(2, result.size());
        assertEquals(mockEmployees, result);
        verify(dao, times(1)).getAllEmployee();
    }
}