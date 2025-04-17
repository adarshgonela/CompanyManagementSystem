package com.adarsh.projectmanagement_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.projectmanagement_service.dto.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{

}
