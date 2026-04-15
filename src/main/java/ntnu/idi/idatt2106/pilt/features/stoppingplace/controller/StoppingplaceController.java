package ntnu.idi.idatt2106.pilt.features.stoppingplace.controller;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Task;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.service.StoppingplaceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * REST Controller for {@link Stoppingplace} endpoints.
 * *
 * <p>
 * Provides an API for the frontend to query game locations and
 * retrieve associated tasks for the player.
 * </p>
 */
@RestController
@RequestMapping("api/stoppingplace")
@RequiredArgsConstructor
public class StoppingplaceController {

  private final StoppingplaceService stoppingplaceService;

  @GetMapping
  public ResponseEntity<List<Stoppingplace>> getAll() {
    return ResponseEntity.ok(stoppingplaceService.getAllStoppingplaces());
  }

  @GetMapping("/unlocked")
  public ResponseEntity<List<Stoppingplace>> getUnlocked() {
    return ResponseEntity.ok(stoppingplaceService.getAllUnlockedPlaces());
  }

  @GetMapping("/{id}/task")
  public ResponseEntity<Task> getTask(@PathVariable Long id) {
    Task task = stoppingplaceService.getTaskForStoppingplace(id);
    return ResponseEntity.ok(task);
  }
}
