package ntnu.idi.idatt2106.pilt.features.notebook;

import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Notebook entities. This interface will be responsible for
 * providing methods to perform CRUD operations on the Notebook entity, as well as any custom queries
 * related to notebooks, such as finding notebooks by student or stopping place.
 */
@Repository
public interface NotebookRepository {


    List<Notebook> findAllByStudentOrderByStoppingPlaceOrderIndexAsc(Student student);

    Optional<Notebook> findByStudentAndStoppingPlace(Student student, Stoppingplace stoppingPlace);

    boolean existsByStudentAndStoppingPlace(Student student, Stoppingplace stoppingPlace);

    List<Notebook> findAllByStudentClassroomId(Long classroomId);

    List<Notebook> findAllByStudentClassroomIdAndReflectionsIsNotNull(Long classroomId);

}
