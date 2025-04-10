package com.adarsh.projectmanagement_service.repo;

import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Projectrepo extends JpaRepository<Project,Long> {
    List<Project> findByNameContainingIgnoreCase(String name);
    List<Project> findByStatus(ProjectStatus status);

    // Custom query to find projects containing a specific employee
    @Query("SELECT p FROM Project p JOIN p.employeeIds e WHERE e = :empid")
    List<Project> findByEmployeeId(@Param("empid") Long empid);

//     @Query("SELECT name FROM Project p WHERE p.name = :name")
// Optional<Project> getdatabyprojectname(@Param("name") String name);

List<Project> findByName(String name);


}
