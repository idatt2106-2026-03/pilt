package ntnu.idi.idatt2106.pilt.features.stoppingplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.TaskProgress;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.TaskStatus;

/**
 * Repository interface for tracking {@link TaskProgress}.
 * *
 * <p>
 * Provides methods to monitor and update the relationship between
 * a {@link Student} and a {@link Task}, including completion status.
 * </p>
 */
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long> {

  Optional<TaskProgress> findByStudentIdAndTaskId(Long studentId, Long taskID);

  List<TaskProgress> findByStudentIdAndTaskStatus(Long studentId, TaskStatus status);
}
