package ru.vsu.cs.yurov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.yurov.models.Project;
import ru.vsu.cs.yurov.repositories.ProjectsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectsService {
    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public List<Project> getAll() {
        return projectsRepository.findAll();
    }

    public Project getById(int id) {
        Project project = projectsRepository.findById(id).orElse(null);
        if (project != null) {
            Hibernate.initialize(project.getTasks());
        }
        return project;
    }

    @Transactional
    public void save(Project project) {
        projectsRepository.save(project);
    }

    @Transactional
    public void update(int id, Project updatedProject) {
        updatedProject.setId(id);
        projectsRepository.save(updatedProject);
    }

    @Transactional
    public void delete(int id) {
        projectsRepository.deleteById(id);
    }
}
