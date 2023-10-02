package ru.vsu.cs.yurov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.yurov.models.Task;
import ru.vsu.cs.yurov.repositories.TasksRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TasksService {
    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> getAll() {
        return tasksRepository.findAll();
    }

    public Task getById(int id) {
        return tasksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Task task) {
        tasksRepository.save(task);
    }

    @Transactional
    public void update(int id, Task updatedTask) {
        Task taskToBeUpdated = tasksRepository.findById(id).get();

        updatedTask.setProject(taskToBeUpdated.getProject());
        updatedTask.setId(id);
        tasksRepository.save(updatedTask);
    }

    @Transactional
    public void delete(int id) {
        tasksRepository.deleteById(id);
    }
}
