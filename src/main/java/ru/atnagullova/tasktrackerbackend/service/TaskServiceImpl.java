package ru.atnagullova.tasktrackerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atnagullova.tasktrackerbackend.entity.Task;
import ru.atnagullova.tasktrackerbackend.repository.TaskRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAllTasksByUser_Id(Long id) {
        return taskRepository.findAllByUser_Id(id);
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
