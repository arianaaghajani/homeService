package ir.ariana.hw19spring.model;

import ir.ariana.hw19spring.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime registrationTime= LocalDateTime.now();
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "firstname can not be null")
    String firstName;
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "lastname can not be null")
    String lastName;
    @Column(unique = true,nullable = false)
    String email;
    @Column(nullable = false,unique = true)
    String password;
    @Enumerated(value = EnumType.STRING)
    Role role;


    public Users(String firstName, String lastName,
                 String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public Users(String email){
        this.email=email;
    }

}
