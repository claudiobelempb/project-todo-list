package br.com.surb.todolist.task.controller;

import br.com.surb.todolist.task.entities.TaskModel;
import br.com.surb.todolist.task.repositories.ITaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks")
public class TaskCreateController {

  private  final ITaskRepository taskRepository;

  public TaskCreateController(ITaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostMapping
  public ResponseEntity handle(@RequestBody TaskModel taskModel){
    TaskModel task = taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }
}
