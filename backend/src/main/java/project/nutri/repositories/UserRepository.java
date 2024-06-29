package project.nutri.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.nutri.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}