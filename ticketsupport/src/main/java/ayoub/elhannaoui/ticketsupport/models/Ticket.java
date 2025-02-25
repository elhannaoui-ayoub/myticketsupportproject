package ayoub.elhannaoui.ticketsupport.models;

import ayoub.elhannaoui.ticketsupport.entities.Comment;
import ayoub.elhannaoui.ticketsupport.entities.User;
import ayoub.elhannaoui.ticketsupport.enums.Category;
import ayoub.elhannaoui.ticketsupport.enums.Priority;
import ayoub.elhannaoui.ticketsupport.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Ticket {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String category;
    private LocalDateTime creationDate;
    private String status;
    private int employeeId;
}
