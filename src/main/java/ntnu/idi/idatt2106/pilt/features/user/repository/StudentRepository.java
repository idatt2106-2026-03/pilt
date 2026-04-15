package ntnu.idi.idatt2106.pilt.features.user.repository;

import java.util.Optional;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByFeideUsername(String feideUsername);
}

