package project.nutri;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.nutri.entities.User;
import project.nutri.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @BeforeEach
    @Transactional
    public void setup() {
        repository.deleteAll();
    }
    
    @Test
    public void findByNameTest() {
        //scenario
        User user = new User(null, "test", "test@gmail.com", "test");
        repository.save(user);
        //action
        Optional<User> result = repository.findByName("TEST");
        //validation
        Assertions.assertThat(result).isPresent();
    }

    @Test
    public void findByNameNonExistentTest() {
        Optional<User> result = repository.findByName("NONEXISTUSER");
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void findByEmailTest() {
        User user = new User(null, "test", "test@gmail.com", "test");
        repository.save(user);
        Optional<User> result = repository.findByEmail("test@gmail.com");
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    public void findByEmailNonExistentTest() {
        Optional<User> result = repository.findByEmail("nonexist@gmail.com");
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void existByNameTest() {
        User user = new User(null, "test", "test@gmail.com", "test");
        repository.save(user);
        boolean result = repository.existsByName("TEST");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void nonExistByNameTest() {
        boolean result = repository.existsByName("NONEXISTUSER");
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void existsByEmail() {
        User user = new User(null, "test", "test@gmail.com", "test");
        repository.save(user);
        boolean result = repository.existsByEmail("test@gmail.com");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void nonExistByEmailTest() {
        boolean result = repository.existsByEmail("nonexist@gmail.com");
        Assertions.assertThat(result).isFalse();
    }
}
