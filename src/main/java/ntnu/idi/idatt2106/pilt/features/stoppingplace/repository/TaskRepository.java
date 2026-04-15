package ntnu.idi.idatt2106.pilt.features.stoppingplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Task;

/**
 * Repository interface for {@link Task} entities.
 * *
 * <p>
 * As the base repository for the Task inheritance hierarchy, this interface
 * allows for CRUD operations across all specialized task types defined
 * in the system.
 * </p>
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

}
