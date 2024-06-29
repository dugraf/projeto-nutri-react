package project.nutri.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.nutri.entities.User;
import project.nutri.exceptions.RulesException;
import project.nutri.repositories.UserRepository;
import project.nutri.utils.Encrypt;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent())
            throw new RulesException("USUÁRIO NÃO ENCONTRADO!");
        return user;
    }

    public Optional<User> findByName(String name) {
        Optional<User> user = repository.findByName(name);
        if (!user.isPresent())
            throw new RulesException("USUÁRIO NÃO ENCONTRADO!");
        return user;
    }

    public User save(User user) {
        emailValidation(user.getEmail());
        return repository.save(user);
    }

    public User update(User user) {
        Objects.requireNonNull(user.getId(), "ID DO USUÁRIO NÃO PODE SER NULO!");
        return repository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id).orElseThrow(() -> new RulesException("USUÁRIO NÃO ENCONTRADO!"));
        repository.deleteById(user.getId());
    }

    private void emailValidation(String email) {
        if(repository.existsByEmail(email))
            throw new RulesException("JÁ EXISTE UM USUÁRIO CADASTRADO COM ESTE E-MAIL!");
    }

    public boolean authentication(String email, String password) {
        Optional<User> user = repository.findByEmail(email);
        if(!user.isPresent())
            throw new RulesException("USUÁRIO NÃO ENCONTRADO!");
        if(Encrypt.validatePassword(password, user.get().getPassword())) {
            user.get().setLastLogin(LocalDateTime.now());
            update(user.get());
            return true;
        }
        return false;
    }
}
