package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService,Long> {
    Optional<SubService> findByName(String name);

    void deleteByName(String jobName);

    @Query(" select s from SubService s where s.mainService.id = :mainServiceId ")
    List<SubService> findByMainServiceId(Long mainServiceId);

    @Query(" select s from SubService s where s.mainService.name = :mainServiceName ")
    List<SubService> findByMainServiceName(String mainServiceName);

}
