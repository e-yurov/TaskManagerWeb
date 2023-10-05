package ru.vsu.cs.yurov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.yurov.models.Project;
import ru.vsu.cs.yurov.models.Task;
import ru.vsu.cs.yurov.repositories.ProjectsRepository;
import ru.vsu.cs.yurov.repositories.TasksRepository;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class TasksService {
    private final TasksRepository tasksRepository;
    private final ProjectsRepository projectsRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository, ProjectsRepository projectsRepository) {
        this.tasksRepository = tasksRepository;
        this.projectsRepository = projectsRepository;
    }

    public List<Task> getAll() {
        return tasksRepository.findAll();
    }

    public Task getById(int id) {
        return tasksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Task task, int projectId) {
        Project project;
        if (projectId == -1) {
            task.setProject(null);
        } else {
            project = projectsRepository.findById(projectId).get();
            task.setProject(project);
            project.getTasks().add(task);
        }

        tasksRepository.save(task);
    }

    @Transactional
    public void update(int taskToBeUpdatedId, Task updatedTask, int projectId) {
        Task taskToBeUpdated = tasksRepository.findById(taskToBeUpdatedId).get();

        Project prevProject = taskToBeUpdated.getProject();
        if (prevProject != null) {
            prevProject.getTasks().remove(updatedTask);
        }

        if (projectId == -1) {
            updatedTask.setProject(null);
        } else {
            Project project = projectsRepository.findById(projectId).get();
            updatedTask.setProject(project);
            updatedTask.setId(taskToBeUpdatedId);
            project.getTasks().add(updatedTask);
        }
        tasksRepository.save(updatedTask);
    }

    @Transactional
    public void delete(int id) {
        tasksRepository.deleteById(id);
    }

    public String calculateLeftTime(Task task) {
        LocalDateTime expiringDateTime = LocalDateTime.ofInstant(
                task.getExpiringDate().toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        Period period = Period.between(now.toLocalDate(), expiringDateTime.toLocalDate());

        int hoursLeft = (Math.abs((int) ((expiringDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) / 3_600_000L)));
        task.setExpired(expiringDateTime.isBefore(now));
        task.setExpiringSoon(hoursLeft < 24);

        String result = "";
        if (period.getYears() > 0) {
            result += period.getYears() + " year(s) ";
        }
        if (period.getMonths() > 0) {
            result += period.getMonths() + " month(s) ";
        }
        if (period.getDays() > 0) {
            result += period.getDays() + " day(s) left";
        } else {
            result = hoursLeft % 24 + " hours left";
        }

        return result;
    }
}
