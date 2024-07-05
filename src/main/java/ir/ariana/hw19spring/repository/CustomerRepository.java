package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    @Modifying
    @Query(" update Customer c set c.password = :newPassword where c.email = :email")
    void editPassword(String email, String newPassword);

    @Modifying
    @Query("update Customer c set c.credit = :newCredit where c.id = :customerId")
    void updateCredit(Long customerId, Long newCredit);

    Optional<Customer> findByPassword(String password);
    Optional<Customer> findByEmailAndPassword(String email,String password);

}
