package br.com.surb.todolist.task.controller;

import br.com.surb.todolist.task.entities.TaskModel;
import br.com.surb.todolist.task.repositories.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class TaskFindAllController {
    private final ITaskRepository taskRepository;

    public TaskFindAllController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<TaskModel> handle(HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return taskRepository.findByIdUser(idUser);
    }
}
