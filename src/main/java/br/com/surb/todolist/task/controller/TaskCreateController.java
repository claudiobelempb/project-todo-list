package br.com.surb.todolist.task.controller;

import br.com.surb.todolist.task.entities.TaskModel;
import br.com.surb.todolist.task.repositories.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class TaskCreateController {

  private  final ITaskRepository taskRepository;

  public TaskCreateController(ITaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostMapping
  public ResponseEntity handle(@RequestBody TaskModel taskModel, HttpServletRequest request){
    UUID idUser = (UUID) request.getAttribute("idUser");
    taskModel.setIdUser(idUser);

    LocalDateTime currentDate = LocalDateTime.now();
    if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início / data de término deve ser maior do que a data atual");
    }
    if(taskModel.getStartAt().isAfter(taskModel.getStartAt())){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser maior do que a data do término");
    }
    TaskModel task = taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }
}
