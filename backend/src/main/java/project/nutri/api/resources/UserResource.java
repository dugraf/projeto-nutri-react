package project.nutri.api.resources;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.nutri.api.dto.UserDTO;
import project.nutri.entities.User;
import project.nutri.exceptions.RulesException;
import project.nutri.services.UserService;
import project.nutri.utils.Encrypt;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Optional<User> user = service.findById(id);
            return ResponseEntity.ok().body(user);
        } catch(RulesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDTO dto) {
        User user = new User(null, dto.getName(), dto.getEmail(), dto.getPassword());
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        try {
            Optional<User> user = service.findById(id);
            user.get().setName(dto.getName().toUpperCase());
            user.get().setEmail(dto.getEmail());
            user.get().setPassword(Encrypt.encoder(dto.getPassword()));
            service.update(user.get());
            return ResponseEntity.ok(user);
        } catch(RulesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch(RulesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestParam String name, @RequestParam String password) {
        try {
            if(service.authentication(name, password))
                return ResponseEntity.ok("AUTENTICAÇÃO FEITA COM SUCESSO");
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USUÁRIO OU SENHA INVÁLIDOS!");
        } catch(RulesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}