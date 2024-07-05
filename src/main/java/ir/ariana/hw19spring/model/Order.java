package ir.ariana.hw19spring.model;

import ir.ariana.hw19spring.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SuperBuilder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime registrationTime=LocalDateTime.now();
    LocalDateTime executionTime;
    LocalDateTime endTime;

    @NotNull(message = "you must enter price")
    Long proposedPrice;
    String description;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    SubService subService;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    List<Offer> offerList;

    @OneToOne
    Comment comment;

    @OneToOne
    Address address;

}
