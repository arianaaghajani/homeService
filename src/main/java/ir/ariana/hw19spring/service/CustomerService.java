package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ir.ariana.hw19spring.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (violations.isEmpty()) {
            customerRepository.save(customer);
            log.info("customer saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Customer> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    @Transactional
    public void saveCustomer(Customer customer) {
         if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            log.error("duplicate email can not insert");
            throw new DuplicateInformationException("duplicate email can not insert");
        } else if (customerRepository.findByPassword(customer.getPassword()).isPresent()) {
            log.error("duplicate password can not insert");
            throw new DuplicateInformationException("duplicate password can not insert");
        } else {
            validate(customer);
        }
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException("customer with id " + id + " not founded"));
    }

    public Customer singInCustomer(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password).orElseThrow(() ->
                new NotFoundException("wrong username or password"));
    }

    public void UpdatePassword(String password,Long id) {
        Customer customer = findById(id);
        customer.setPassword(password);
        validate(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }


}