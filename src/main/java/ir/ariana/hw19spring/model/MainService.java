package ir.ariana.hw19spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime registrationTime= LocalDateTime.now();
    @Column(nullable = false,unique = true)
    String name;
    @OneToMany(mappedBy = "mainService",cascade = CascadeType.ALL)
    List<SubService> subServiceList;


    public MainService(String name) {
        this.name = name;
    }
}
