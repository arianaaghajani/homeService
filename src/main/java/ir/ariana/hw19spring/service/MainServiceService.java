package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.MainService;
import ir.ariana.hw19spring.repository.MainServiceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceService {
    private static  MainServiceRepository mainServiceRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();


    public void validate(MainService mainService) {
        Set<ConstraintViolation<MainService>> violations = validator.validate(mainService);
        if (violations.isEmpty()) {
            mainServiceRepository.save(mainService);
            log.info("mainService saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<MainService> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }


    public void saveMainService(MainService mainService) {
        if (mainServiceRepository.findByName(mainService.getName()).isPresent()) {
            log.error("duplicate mainService name can not insert");
            throw new DuplicateInformationException("duplicate mainService name can not insert");
        } else {
            validate(mainService);
        }
    }

    public static MainService findById(Long id) {
        return mainServiceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("mainService with id " + id + " not found"));
    }

    public List<MainService> showAllMainService() {
        return mainServiceRepository.findAll();
    }

    public void removeMainService(Long id) {
        MainService mainService  = findById(id);
        mainServiceRepository.delete(mainService);
    }

}
