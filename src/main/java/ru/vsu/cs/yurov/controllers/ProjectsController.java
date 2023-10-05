package ru.vsu.cs.yurov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.yurov.models.Project;
import ru.vsu.cs.yurov.services.ProjectsService;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjectsService projectsService;

    @Autowired
    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("projects", projectsService.getAll());
        return "projects/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", projectsService.getById(id));

        return "projects/show";
    }

    @GetMapping("/new")
    public String newProject(@ModelAttribute("project") Project project) {
        return "projects/new";
    }

    @PostMapping
    public String create(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "projects/new";
        }

        projectsService.save(project);
        return "redirect:/projects";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        projectsService.delete(id);
        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", projectsService.getById(id));
        return "projects/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("project") @Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "projects/edit";
        }

        projectsService.update(id, project);
        return "redirect:/projects/" + id;
    }
}
