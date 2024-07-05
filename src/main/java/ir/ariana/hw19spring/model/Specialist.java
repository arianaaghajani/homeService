package ir.ariana.hw19spring.model;

import ir.ariana.hw19spring.enums.SpecialistStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
public class Specialist extends Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
    LocalDateTime registrationTime= LocalDateTime.now();
    @Lob
    @Size(max = 300000, message = "it is  big file for image")
    @NotEmpty(message = "must upload an image")
    byte[] image;
    double score;
    Long credit;

    String province;
    @Enumerated(value = EnumType.STRING)
    SpecialistStatus specialistStatus;
    @ManyToMany
    List<SubService> subServiceList;
    @OneToMany(mappedBy = "specialist")
    List<Offer> offerList;

}
