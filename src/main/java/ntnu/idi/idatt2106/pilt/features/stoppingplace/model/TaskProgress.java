package ntnu.idi.idatt2106.pilt.features.stoppingplace.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Track a players progress of a task.
 **/
@Entity
public class TaskProgress {
  /**
   * Unique identifier for the task.
   */
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
   * Star rating (0–3).
   */
  @Getter
  @Setter
  private int stars;

  /**
   * Track progress steps.
   **/
  @Getter
  private int progress;

  /**
   * Time of task completion.
   **/
  @Getter
  private LocalDateTime completedA;
}
