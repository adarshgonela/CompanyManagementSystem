package com.adarsh.projectmanagement_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.projectmanagement_service.dto.Task;
import com.adarsh.projectmanagement_service.service.TaskService;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    public Task createTask(Task task) {
    return taskService.createTask(task);
    }
}
