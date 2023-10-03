package ru.vsu.cs.yurov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.yurov.models.Task;
import ru.vsu.cs.yurov.services.ProjectsService;
import ru.vsu.cs.yurov.services.TasksService;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ProjectsService projectsService;
    private final TasksService tasksService;

    @Autowired
    public TasksController(ProjectsService projectsService, TasksService tasksService) {
        this.projectsService = projectsService;
        this.tasksService = tasksService;
    }

    /*@GetMapping
    public String test(@PathVariable int projectId, Model model) {
        model.addAttribute("project", projectsService.getById(projectId));
        return "tasks/index";
    }*/

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", tasksService.getAll());
        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("task", tasksService.getById(id));
        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task) {
        return "tasks/new";
    }

    @PostMapping
    public String create(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasks/new";
        }

        tasksService.save(task);
        return "redirect:/tasks";
    }
}
