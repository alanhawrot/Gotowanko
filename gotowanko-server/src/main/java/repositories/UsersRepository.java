package repositories;

import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by alanhawrot on 22.03.15.
 */
public interface UsersRepository extends JpaRepository<User, Long> {
}
