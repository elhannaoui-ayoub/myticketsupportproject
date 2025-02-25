package ayoub.elhannaoui.ticketsupport.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private int ticketId;
    private String oldStatus;
    private String newStatus;
    private int changedBy;
    private LocalDateTime changeDate;

    @ManyToOne()
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties("history")
    private Ticket ticket;
}
