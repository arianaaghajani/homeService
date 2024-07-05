package ir.ariana.hw19spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime registrationTime= LocalDateTime.now();
    @Column(unique = true,nullable = false)
    @Size(max = 20, min = 3, message = "Invalid name. Size should between 3 to 20.")
    String name;
    Long basePrice;
    String description;
    @ManyToOne(cascade = CascadeType.ALL)
    MainService mainService;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<Specialist> specialistList;

    @OneToMany(mappedBy = "subService",cascade = CascadeType.ALL)
    List<Order> orderList;

}
