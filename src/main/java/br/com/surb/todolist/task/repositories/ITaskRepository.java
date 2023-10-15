package br.com.surb.todolist.task.repositories;

import br.com.surb.todolist.task.entities.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
    //TaskModel findByIdAndByIdUser(UUID id, UUID idUser);
}
