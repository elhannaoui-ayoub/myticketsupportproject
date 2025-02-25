package ayoub.elhannaoui.ticketsupport.entities;

import ayoub.elhannaoui.ticketsupport.enums.Category;
import ayoub.elhannaoui.ticketsupport.enums.Priority;
import ayoub.elhannaoui.ticketsupport.enums.Status;
import ayoub.elhannaoui.ticketsupport.entities.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tickets")
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime creation_date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Transient
    private User employee;

    private int employee_id;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties("ticket")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties("ticket")
    private Set<History> history = new HashSet<>();
}
