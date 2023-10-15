package br.com.surb.todolist.task.controller;

import br.com.surb.todolist.task.entities.TaskModel;
import br.com.surb.todolist.task.repositories.ITaskRepository;
import br.com.surb.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class TaskUpdateController {
    private final ITaskRepository taskRepository;

    public TaskUpdateController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity handle(@RequestBody TaskModel taskModel, @PathVariable UUID id,  HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        TaskModel task = taskRepository.findById(id).orElse(null);

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada!");
        }

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não  tem permissão para aterar essa tarefe!");
        }

        Utils.copyNonNullProperties(taskModel,task);
        TaskModel entity = taskRepository.save(task);

        return ResponseEntity.ok().body(entity);
    }
}
