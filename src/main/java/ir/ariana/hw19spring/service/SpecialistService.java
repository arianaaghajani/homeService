package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.SpecialistStatus;
import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.Specialist;
import ir.ariana.hw19spring.repository.SpecialistRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialistService {
    private final SpecialistRepository specialistRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Specialist specialist) {
        Set<ConstraintViolation<Specialist>> violations = validator.validate(specialist);
        if (violations.isEmpty()) {
            specialistRepository.save(specialist);
            log.info("specialist saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Specialist> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }


    @Transactional
    public void saveSpecialist(Specialist specialist) {
        if (specialistRepository.findByEmail(specialist.getEmail()).isPresent()) {
            log.error("duplicate email can not insert");
            throw new DuplicateInformationException("duplicate email can not insert");
        } else
            validate(specialist);
    }

    @Transactional
    public Specialist signInSpecialist(String email, String password) {
        return specialistRepository.findByEmailAndPassword(email, password).orElseThrow(() ->
                new NotFoundException("wrong email or password"));
    }

    public Specialist findById(Long id) {
        return specialistRepository.findById(id).orElseThrow(() ->
                new NotFoundException("specialist with id " + id + " not founded"));
    }

    public void UpdatePassword(String password,Long id) {
        Specialist specialist = findById(id);
        specialist.setPassword(password);
        validate(specialist);
    }

    public void updateSpecialistStatus(SpecialistStatus specialistStatus, Long id) {
        Specialist specialist = findById(id);
        specialist.setSpecialistStatus(specialistStatus);
        validate(specialist);
    }



    @Transactional
    public List<Specialist> findBySpecialistStatus(SpecialistStatus specialistStatus) {
        List<Specialist> specialists = specialistRepository.findBySpecialistStatus(specialistStatus);
        if (!specialists.isEmpty())
            return specialists;
        else
            throw new NullPointerException();
    }

    public boolean access(Specialist specialist) {
        SpecialistStatus specialistStatus  = specialist.getSpecialistStatus();
        return specialistStatus ==SpecialistStatus.CONFIRMED;
    }

    public void removeSpecialist(Long id) {
        Specialist specialist= findById(id);
        specialistRepository.delete(specialist);
    }

}
