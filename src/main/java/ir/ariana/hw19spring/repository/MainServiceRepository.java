package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.model.MainService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainServiceRepository extends JpaRepository<MainService,Long> {
    Optional<MainService> findByName(String mainServiceName);

    void deleteByName(String mainServiceName);
}
