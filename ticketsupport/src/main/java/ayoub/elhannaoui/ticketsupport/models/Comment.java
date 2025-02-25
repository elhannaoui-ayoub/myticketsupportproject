package ayoub.elhannaoui.ticketsupport.models;

import ayoub.elhannaoui.ticketsupport.entities.Ticket;
import ayoub.elhannaoui.ticketsupport.entities.User;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private String content;
    private LocalDateTime timestamp;


    private Ticket ticket;


    private User user;
}
