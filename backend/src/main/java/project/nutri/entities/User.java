package project.nutri.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.nutri.utils.Encrypt;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;

    public User(Long id, String name, String email, String password, LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.id = id;
        this.name = name.toUpperCase();
        this.email = email;
        this.password = Encrypt.encoder(password);
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }
}
