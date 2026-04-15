package ntnu.idi.idatt2106.pilt.features.stoppingplace.service;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Task;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.repository.StoppingplaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Service class for managing {@code Stoppingplace} business logic.
 * *
 * <p>
 * Handles data processing, filtering of unlocked locations, and
 * coordinates between repositories to retrieve task-related information.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StoppingplaceService {

  private final StoppingplaceRepository stoppingplaceRepository;

  public List<Stoppingplace> getAllStoppingplaces() {
    return stoppingplaceRepository.findAll();
  }

  /**
   * Business Logic: Get all places that are currently unlocked.
   */
  public List<Stoppingplace> getAllUnlockedPlaces() {
    return stoppingplaceRepository.findByLocked(false);
  }

  /**
   * Logic to find a task associated with a location.
   * Throws an exception if not found, which the Controller can catch.
   */
  public Task getTaskForStoppingplace(Long id) {
    return stoppingplaceRepository.findById(id)
        .map(Stoppingplace::getTask)
        .orElseThrow(() -> new EntityNotFoundException("Stoppingplace or Task not found for id: " + id));
  }
}
