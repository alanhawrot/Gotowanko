package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.Comment;

/**
 * Created by michal on 03.05.15.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
