package com.adarsh.projectmanagement_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.projectmanagement_service.dto.Task;
import com.adarsh.projectmanagement_service.service.TaskService;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
    return taskService.createTask(task);
    }

    @PatchMapping("/update/{id}")
    public Task tasksUpdate(@PathVariable Long id,@RequestBody Task task){
        return taskService.taskstatusUpdate(id,task);
    }
   
}
