package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
