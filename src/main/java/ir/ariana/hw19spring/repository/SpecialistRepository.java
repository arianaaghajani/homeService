package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.enums.SpecialistStatus;
import ir.ariana.hw19spring.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist,Long> {
    boolean existsByEmail(String email);

    Optional<Specialist>findByEmail(String email);

    @Modifying
    @Query(" update Specialist s set s.password = :newPassword where s.id = :SpecialistId ")
    void editPassword(Long SpecialistId, String newPassword);

    @Modifying
    @Query(" update Specialist s set s.credit = :credit where s.id = :id ")
    void updateCredit(Long credit,Long id);


    Optional<Specialist> findByEmailAndPassword(String email,String password);

    List<Specialist> findBySpecialistStatus(SpecialistStatus specialistStatus);

}
