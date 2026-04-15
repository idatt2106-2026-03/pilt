package ntnu.idi.idatt2106.pilt.features.user.repository;

import java.util.Optional;
import ntnu.idi.idatt2106.pilt.features.user.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  Optional<Teacher> findBySchoolEmail(String schoolEmail);
}

