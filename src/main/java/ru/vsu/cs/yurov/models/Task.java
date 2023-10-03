package ru.vsu.cs.yurov.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50")
    private String name;

    @Column(name = "description")
    @Size(min = 2, max = 300, message = "Description length should be between 2 and 300")
    private String description;

    @Column(name = "expiring_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date expiringDate;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public Task() {}

    public Task(String name, String description, Date expiringDate) {
        this.name = name;
        this.description = description;
        this.expiringDate = expiringDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public String getFormattedExpiringDate() {
        //return expiringDate.format(DateTimeFormatter.ofPattern("d/MM/yyyy hh:mm"));
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(expiringDate);
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
