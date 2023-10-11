package br.com.surb.todolist.task.entities;

import br.com.surb.todolist.user.entities.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tasks")
public class TaskModel implements Serializable {

  private static final long serialVersionUID = 3880175677659414000L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID taskId;

  @Column(length = 50)
  private String title;
  private String description;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private String priority;
  @CreationTimestamp
  private LocalDateTime createdAt;

  private UUID idUser;

}
