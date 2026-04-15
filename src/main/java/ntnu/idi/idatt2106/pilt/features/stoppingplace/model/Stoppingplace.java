package ntnu.idi.idatt2106.pilt.features.stoppingplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents a location in the game where a player can stop and perform a task.
 *
 * <p>
 * A Stoppingplace contains a task that is triggered when a player enters the
 * location.
 * <p>
 *
 * @author Tord Fosse
 * @see Task
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stoppingplace {

  /**
   * Unique identifier for the stopping place.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name of the stopping place.
   */
  private String name;

  /**
   * The task associated with this stopping place.
   */
  @OneToOne
  private Task task;

  /**
   * If a task is locked or open.
   **/
  private boolean locked;

  private int requiredStars;

  protected Stoppingplace() {
  }

  public Stoppingplace(String name, Task task, int requiredStars) {
    this.name = name;
    this.task = task;
    this.requiredStars = requiredStars;
  }

}
