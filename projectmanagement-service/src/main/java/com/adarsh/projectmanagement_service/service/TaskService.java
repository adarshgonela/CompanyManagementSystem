package com.adarsh.projectmanagement_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.projectmanagement_service.Exceptions.EmployeeNotFound;
import com.adarsh.projectmanagement_service.clientmethods.FeignclientmethodsEMS;
import com.adarsh.projectmanagement_service.dao.TaskDao;
import com.adarsh.projectmanagement_service.dto.Task;

@Service
public class TaskService {

  private final TaskDao taskDao;
  private final FeignclientmethodsEMS feignclientmethodsEMS;

  @Autowired
  public TaskService(TaskDao taskDao, FeignclientmethodsEMS feignclientmethodsEMS) {
    this.taskDao = taskDao;
    this.feignclientmethodsEMS = feignclientmethodsEMS;
  }

  public Task createTask(Task task) {
    feignclientmethodsEMS.getEmployeeByIdpost(task.getEmpId())
        .orElseThrow(() -> new EmployeeNotFound("employee not found"));
    return taskDao.createTask(task);
  }

  public Task tasksUpdate(Long id, Task task) {
    return taskDao.updateTask(id, task);
  }

  public Task getTaskUsingID(Long id) {
    return taskDao.getTaskById(id);
  }

  public void deleteTask(Long id) {
    taskDao.deleteTask(id);
  }

  public List<Task> getalltasks() {
    return taskDao.getAllTasks();
  }

}
