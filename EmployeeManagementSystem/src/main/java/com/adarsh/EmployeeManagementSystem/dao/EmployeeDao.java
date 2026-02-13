package com.adarsh.EmployeeManagementSystem.dao;

import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.adarsh.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Repository

public class EmployeeDao {
 private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeDao(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        logger.debug("Fetching employee by ID: {}", id);
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        logger.info("Employee created with ID: {}", saved.getId());
        return saved;
    }

    public Employee updateEmployee(Employee employee, Long empId) {
        logger.debug("Updating employee with ID: {}", empId);

        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + empId));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setHireDate(employee.getHireDate());

        Employee updated = employeeRepository.save(existingEmployee);
        logger.info("Employee updated with ID: {}", updated.getId());
        return updated;
    }

    public void deleteEmployee(Long id) {
        logger.debug("Deleting employee with ID: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
        logger.info("Employee with ID {} deleted", id);
    }

    public List<Employee> getAllEmployee() {
        logger.debug("Fetching all employees");
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        logger.debug("Fetching employees - Page: {}, Size: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize); // 0-based indexing
        return employeeRepository.findAll(pageable).getContent();
    }

public Optional<Employee> changeGender(Employee e,Long id){
    Optional<Employee> employeeOptional = employeeRepository.findById(id);
    if(employeeOptional.isPresent()){
        Employee employee=employeeOptional.get();
        employee.setGender(e.getGender());
        employeeRepository.save(employee);
        return employeeOptional;
    }
    return Optional.empty();
}


    // @PostMapping("/upload")
    public ResponseEntity<String> uploadImage( MultipartFile file) throws IOException {
        Optional<Employee> employeeOptional = employeeRepository.findById(1L); // Example: using a fixed ID for demonstration
    
    if (!employeeOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee with ID " + 1L + " not found.");
    }

    Employee employee = employeeOptional.get();

    // Set the image bytes directly or use compressBytes(file.getBytes())
    employee.setProfilePic(file.getBytes());

    employeeRepository.save(employee);

    System.out.println("Updated Employee: " + employee);

    return ResponseEntity.ok("Profile image uploaded successfully.");

    }

    // @GetMapping(path = {"/get/{imageName}"})
    public Employee getImage( String imageName) throws IOException {

        final Optional<Employee> retrievedImage = employeeRepository.findById(1L);
        Employee img = new Employee(retrievedImage.get().getLastName(),
                decompressBytes(retrievedImage.get().getProfilePic()));
        return img;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
        }
        return outputStream.toByteArray();
    }

    public List<Employee> findByEmployeePosition(String position,Long lastId,int pageSize) {
         Pageable pageable = PageRequest.of(0, pageSize); // only page size matters for keyset pagination

        List<Employee> employees = employeeRepository.findEmployeeByPosition(position, lastId, pageable);

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found with position: " + position);
        }

        return employees;
    }
    public List<Employee> findByDepartment(String department,Long lastId,int pageSize)
    {
         Pageable pageable = PageRequest.of(0, pageSize); // only page size matters for keyset pagination

        List<Employee> employees = employeeRepository.findEmployeeByDepartment(department, lastId, pageable);

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found with department: " + department);
        }

        return employees;
    }

}
