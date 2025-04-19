package com.adarsh.projectmanagement_service.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adarsh.projectmanagement_service.Exceptions.ProjectNotFoundException;
import com.adarsh.projectmanagement_service.Exceptions.ResourceNotFoundException;
import com.adarsh.projectmanagement_service.Exceptions.TaskNotFoundExceptions;
import com.adarsh.projectmanagement_service.dto.Project;
import com.adarsh.projectmanagement_service.dto.Task;
import com.adarsh.projectmanagement_service.repo.Projectrepo;
import com.adarsh.projectmanagement_service.repo.TaskRepository;

@Repository
public class TaskDao {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Projectrepo projectRepository;

    // Create a task
    public Task createTask(Task task) {
        Long projectId = task.getProject().getId(); // ✅ Get the project ID from the incoming JSON

        Project existingProject = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException("Project not found to create task"));
    
        task.setProject(existingProject); // ✅ Attach managed project entity
    
        return taskRepository.save(task); // ✅ Now it's safe to persist the task   
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
    public Task updateTask(Long id, Task task) {
        Optional<Task> optional = taskRepository.findById(id);

        if (optional.isPresent()) {
            Task t = optional.get();

            if (task.getDescription() != null)
                t.setDescription(task.getDescription());

            if (task.getCreatedAt() != null)
                t.setCreatedAt(task.getCreatedAt());

            // if (task.getEmpId() != null)
            // t.setEmpId(task.getEmpId());

            if (task.getStatus() != null)
                t.setStatus(task.getStatus());

            if (task.getTitle() != null)
                t.setTitle(task.getTitle());

            if (task.getUpdatedAt() != null)
                t.setUpdatedAt(task.getUpdatedAt());

            return taskRepository.save(t);
        } else {
            throw new TaskNotFoundExceptions("The task you are trying to update is not found");
        }
    }

    // Delete a task
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundExceptions("Task not found with id: " + id));
        taskRepository.delete(task);
    }

}
