package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.Comment;
import ir.ariana.hw19spring.repository.CommentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Comment comment) {
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        if (violations.isEmpty()) {
            commentRepository.save(comment);
            log.info("comment saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Comment> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    public void saveComment(Comment comment) {
        validate(comment);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("comment with id " + id + " not founded"));
    }
}
