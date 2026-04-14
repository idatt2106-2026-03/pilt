package ntnu.idi.idatt2106.pilt.features.stoppingplace.model;

import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Track a players progress of a task.
 **/
@Entity
@NoArgsConstructor
public class TaskProgress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Student student;

  @ManyToOne
  private Task task;

  /**
   * Whether the player has completed, or started the task.
   */
  @Getter
  @Setter
  private TaskStatus taskStatus;

  /**
   * Time of task completion.
   **/
  @Getter
  private LocalDateTime completedAt;

  public TaskProgress(Student student, Task task) {
    this.student = student;
    this.task = task;
  }
}
