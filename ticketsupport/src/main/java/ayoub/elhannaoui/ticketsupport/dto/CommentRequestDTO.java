package ayoub.elhannaoui.ticketsupport.dto;

import ayoub.elhannaoui.ticketsupport.entities.Ticket;
import ayoub.elhannaoui.ticketsupport.entities.User;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private int ticket_id;
    private int user_id;
}
