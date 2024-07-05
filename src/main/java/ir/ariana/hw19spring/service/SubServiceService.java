package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.MainService;
import ir.ariana.hw19spring.model.SubService;
import ir.ariana.hw19spring.repository.SubServiceRepository;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class SubServiceService {
    private final SubServiceRepository subServiceRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(SubService subService) {
        Set<ConstraintViolation<SubService>> violations = validator.validate(subService);
        if (violations.isEmpty()) {
            subServiceRepository.save(subService);
            log.info("subService saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<SubService> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    public void saveSubService(SubService subService) {
        if (subServiceRepository.findByName(subService.getName()).isPresent()) {
            log.error("duplicate subService name can not insert");
            throw new DuplicateInformationException("duplicate subService name can not insert");
        } else {
            validate(subService);
        }
    }

    public SubService findById(Long id) {
        return subServiceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("subService with id " + id + " not founded"));
    }

    public void updateSubServicePrice(Long price,Long id) {
        SubService subService = findById(id);
        subService.setBasePrice(price);
        validate(subService);
    }

    public void updateSubServiceDescription(String description,Long id) {
        SubService subService= findById(id);
        subService.setDescription(description);
        validate(subService);
    }

    public List<SubService> findByMainService(Long mainServiceId) {
        List<SubService> subServices = subServiceRepository.findByMainServiceId(mainServiceId);
        if (!subServices.isEmpty())
            return subServices;
        else
            throw new NullPointerException();
    }

    public void removeSubService(SubService subService) {
        subServiceRepository.delete(subService);
    }
}
