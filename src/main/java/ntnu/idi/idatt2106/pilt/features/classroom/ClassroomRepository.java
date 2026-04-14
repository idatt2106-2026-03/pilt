package ntnu.idi.idatt2106.pilt.features.classroom;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByClassCode(String classCode);
}


