package com.adarsh.EmployeeManagementSystem.dao;

import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.adarsh.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long empid) {

        Employee existingEmployee = employeeRepository.findById(empid)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + empid));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setHireDate(employee.getHireDate());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;

        return entityManager.createQuery("SELECT e FROM Employee e ORDER BY e.id", Employee.class)
                            .setFirstResult(offset)
                        .setMaxResults(pageSize)
                        .getResultList();
}

}
