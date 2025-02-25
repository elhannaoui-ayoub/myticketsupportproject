package ayoub.elhannaoui.ticketsupport.web;

import ayoub.elhannaoui.ticketsupport.dto.CommentRequestDTO;
import ayoub.elhannaoui.ticketsupport.dto.TicketRequestDTO;
import ayoub.elhannaoui.ticketsupport.entities.Comment;
import ayoub.elhannaoui.ticketsupport.entities.Ticket;
import ayoub.elhannaoui.ticketsupport.enums.Category;
import ayoub.elhannaoui.ticketsupport.enums.Priority;
import ayoub.elhannaoui.ticketsupport.repositories.CommentRepository;
import ayoub.elhannaoui.ticketsupport.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class CommentRestController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping("/comments/list")
    public List<Comment> findAllOrders(){
        List<Comment> comments = commentRepository.findAll();

        return comments;
    }

    @PostMapping("/comments/new")
    public Comment newComment(@RequestBody CommentRequestDTO commentRequestDTO){
        Comment comment = new Comment();

        comment.setTicket(ticketRepository.findById(Long.valueOf(commentRequestDTO.getTicket_id())).get());
        comment.setUser_id(commentRequestDTO.getUser_id());
        comment.setContent(commentRequestDTO.getContent());
        comment.setTimestamp(LocalDateTime.now());
        System.out.println(comment);
        commentRepository.save(comment);
        return comment;
    }
}
