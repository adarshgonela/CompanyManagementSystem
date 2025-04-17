package com.adarsh.projectmanagement_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.projectmanagement_service.dao.TaskDao;
import com.adarsh.projectmanagement_service.dto.Task;

@Service
public class TaskService {
@Autowired
private TaskDao taskDao;
    public Task createTask(Task task) {
       return taskDao.createTask(task);
    }

}
