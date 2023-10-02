package ru.vsu.cs.yurov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.yurov.models.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
}
