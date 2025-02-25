package ayoub.elhannaoui.ticketsupport.repositories;

import ayoub.elhannaoui.ticketsupport.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
