package com.adarsh.projectmanagement_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adarsh.projectmanagement_service.dto.Task;
import com.adarsh.projectmanagement_service.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.tasksUpdate(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getTaskUsingID(@PathVariable Long id) {
        Task getTask = taskService.getTaskUsingID(id);
        return ResponseEntity.ok(getTask);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
     taskService.deleteTask(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getalltasks(){
        List<Task> task= taskService.getalltasks();
        return ResponseEntity.ok(task);
    }

}
