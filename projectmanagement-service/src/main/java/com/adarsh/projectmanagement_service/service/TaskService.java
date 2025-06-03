package com.adarsh.projectmanagement_service.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.adarsh.projectmanagement_service.Exceptions.EmployeeNotFound;
import com.adarsh.projectmanagement_service.clientmethods.FeignclientmethodsEMS;
import com.adarsh.projectmanagement_service.dao.TaskDao;
import com.adarsh.projectmanagement_service.dto.Task;

@Service
public class TaskService {

  private final TaskDao taskDao;
  private final FeignclientmethodsEMS feignclientmethodsEMS;

  public TaskService(TaskDao taskDao, FeignclientmethodsEMS feignclientmethodsEMS) {
    this.taskDao = taskDao;
    this.feignclientmethodsEMS = feignclientmethodsEMS;
  }

  @CachePut(value = "tasks", key = "#result.id")
  public Task createTask(Task task) {
    feignclientmethodsEMS.getEmployeeByIdpost(task.getEmpId())
        .orElseThrow(() -> new EmployeeNotFound("employee not found"));
    return taskDao.createTask(task);
  }

  @CachePut(value = "tasks", key = "#id")
  public Task tasksUpdate(Long id, Task task) {
    return taskDao.updateTask(id, task);
  }

  @Cacheable(value = "tasks", key = "#id")
  public Task getTaskUsingID(Long id) {
    return taskDao.getTaskById(id);
  }

  @CacheEvict(value = "tasks", key = "#id")
  public void deleteTask(Long id) {
    taskDao.deleteTask(id);
  }

  @Cacheable(value = "allTasks")
  public List<Task> getalltasks() {
    return taskDao.getAllTasks();
  }

}
