package com.adarsh.projectmanagement_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.projectmanagement_service.Exceptions.EmployeeNotFound;
import com.adarsh.projectmanagement_service.clientmethods.FeignclientmethodsEMS;
import com.adarsh.projectmanagement_service.dao.TaskDao;
import com.adarsh.projectmanagement_service.dto.Task;

@Service
public class TaskService {

@Autowired
private TaskDao taskDao;

@Autowired
private FeignclientmethodsEMS feignclientmethodsEMS;

    public Task createTask(Task task) {
feignclientmethodsEMS.getEmployeeByIdpost(task.getEmpId()).orElseThrow(() -> new EmployeeNotFound("employee not found"));
       return taskDao.createTask(task);
    }

    public Task taskstatusUpdate(Long id,Task task) {
return taskDao.updateTask(id,task);  
  }

}
