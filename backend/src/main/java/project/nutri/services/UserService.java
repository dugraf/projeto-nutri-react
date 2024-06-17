package project.nutri.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.nutri.entities.User;
import project.nutri.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }

    public User findByName(String name) {
        return repository.findByName(name);
    }

    public void saveOrUpdate(User user) {
        if(user.getId() == null)
            repository.save(user);
        else {
            User existingUser = repository.findById(user.getId()).orElse(null);
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setLastLogin(user.getLastLogin());
            repository.save(existingUser);
        }
    }    

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
