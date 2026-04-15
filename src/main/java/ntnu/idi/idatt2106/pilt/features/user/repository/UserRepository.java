package ntnu.idi.idatt2106.pilt.features.user.repository;

import ntnu.idi.idatt2106.pilt.features.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
