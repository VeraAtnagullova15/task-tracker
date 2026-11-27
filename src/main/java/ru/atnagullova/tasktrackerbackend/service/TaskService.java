package ru.atnagullova.tasktrackerbackend.service;

import ru.atnagullova.tasktrackerbackend.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllTasksByUser_Id(Long id);

    void createTask(Task task);

    void deleteTask(Task task);
}
