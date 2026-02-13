package com.adarsh.EmployeeManagementSystem.Repo;

import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("""
       SELECT e FROM Employee e
       WHERE e.position LIKE CONCAT('%',:position,'%')
       AND e.id > :lastId
       ORDER BY e.id ASC
       """)
           List<Employee> findEmployeeByPosition(@Param("position")String position,@Param("lastId")Long lastId,Pageable pageable);

 @Query("""
       SELECT e FROM Employee e
       WHERE e.department LIKE CONCAT('%',:department,'%')
       AND e.id > :lastId
       ORDER BY e.id ASC
       """)
           List<Employee> findEmployeeByDepartment(@Param("department")String department,@Param("lastId")Long lastId,Pageable pageable);


        }
