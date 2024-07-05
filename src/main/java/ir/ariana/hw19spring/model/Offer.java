package ir.ariana.hw19spring.model;

import ir.ariana.hw19spring.enums.OfferStatus;
import jakarta.persistence.*;
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
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime registrationTime= LocalDateTime.now();
    LocalDateTime executionTime;
    LocalDateTime endTime;
    Long proposedPrice;
    @Enumerated(value = EnumType.STRING)
    OfferStatus offerStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    Order order;
    @ManyToOne
    Specialist specialist;

}
