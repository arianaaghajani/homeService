package ir.ariana.hw19spring.model;


import ir.ariana.hw19spring.enums.CustomerStatus;
import ir.ariana.hw19spring.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends Users{
    Long credit;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Address> addressList;
    @Enumerated(value = EnumType.STRING)
    CustomerStatus customerStatus;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @ToString.Exclude
    List<Order>orderList;


    public Customer(String firstName, String lastName, String email, String password, Role role) {
        super(firstName, lastName, email, password, role);
    }

}
