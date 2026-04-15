package ntnu.idi.idatt2106.pilt.features.stoppingplace.repository;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Stoppingplace} entities.
 * Provides standard CRUD operations and custom query methods for location data.
 */
@Repository
public interface StoppingplaceRepository extends JpaRepository<Stoppingplace, Long> {

  List<Stoppingplace> findByLocked(boolean locked);

  List<Stoppingplace> findByRequiredStarsLessThanEqual(int stars);
}
