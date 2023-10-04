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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", tasksService.getAll());
        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Task task = tasksService.getById(id);
        model.addAttribute("task", task);
        model.addAttribute("leftHours", tasksService.calculateLeftHours(task));
        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task, Model model) {
        model.addAttribute("projects", projectsService.getAll());
        return "tasks/new";
    }

    @PostMapping
    public String create(@RequestParam("projectId") Integer projectId,
                         @ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasks/new";
        }

        tasksService.save(task, projectId);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        tasksService.delete(id);

        return "redirect:/tasks";
    }
}
