package com.adarsh.projectmanagement_service.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adarsh.projectmanagement_service.Exceptions.ResourceNotFoundException;
import com.adarsh.projectmanagement_service.dto.Task;
import com.adarsh.projectmanagement_service.repo.TaskRepository;
@Repository
public class TaskDao {
@Autowired
    private TaskRepository taskRepository;

    // Create a task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    // Get task by ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
    

    // Update a task
    public Task updateTask(Long id, Task taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
       return taskRepository.save(task);
    }

    // Delete a task
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

   

   
}
