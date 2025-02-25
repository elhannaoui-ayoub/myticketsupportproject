package ayoub.elhannaoui.ticketsupport.web;


import ayoub.elhannaoui.ticketsupport.dto.TicketRequestDTO;
import ayoub.elhannaoui.ticketsupport.entities.Comment;
import ayoub.elhannaoui.ticketsupport.entities.History;
import ayoub.elhannaoui.ticketsupport.entities.Ticket;
import ayoub.elhannaoui.ticketsupport.enums.Category;
import ayoub.elhannaoui.ticketsupport.enums.Priority;
import ayoub.elhannaoui.ticketsupport.enums.Status;
import ayoub.elhannaoui.ticketsupport.repositories.CommentRepository;
import ayoub.elhannaoui.ticketsupport.repositories.HistoryRepository;
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
public class TicketRestController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TicketRepository ticketRepository ;
    @Autowired
    private HistoryRepository historyRepository;
    @GetMapping("/tickets/list")
    public List<Ticket> findAllOrders(){
        List<Ticket> tickets = ticketRepository.findAll();
        List<Comment> comments = commentRepository.findAll();
        tickets.forEach(tic->{
            comments.forEach(com->{
                if(com.getTicket().getId()==tic.getId()){
                    tic.getComments().add(com);
                }
            });
        });
        return tickets;
    }

    @PostMapping("/tickets/new")
    public Ticket newTicket(@RequestBody TicketRequestDTO ticketRequestDTO){
        Ticket ticket = new Ticket();
        ticket.setDescription(ticketRequestDTO.getDescription());
        ticket.setCategory(Category.valueOf(ticketRequestDTO.getCategory()));
        ticket.setPriority(Priority.valueOf(ticketRequestDTO.getPriority()));
        ticket.setTitle(ticketRequestDTO.getTitle());
        ticket.setEmployee_id(ticketRequestDTO.getEmployee_id());
        System.out.println(ticket);
        ticketRepository.save(ticket);
        return ticket;
    }

    @PostMapping("/tickets/edit")
    public Ticket editTicket(@RequestBody TicketRequestDTO ticketRequestDTO){
        Optional<Ticket> ticket = ticketRepository.findById(Long.valueOf(ticketRequestDTO.getId()));
        History history = new History();
        history.setOldStatus(ticket.get().getStatus().toString());
        history.setNewStatus(ticketRequestDTO.getStatus());
        history.setChangedBy(ticketRequestDTO.getIt_support_id());
        history.setTicket(ticket.get());
        history.setChangeDate(LocalDateTime.now());
        historyRepository.save(history);
        ticket.get().setStatus(Status.valueOf(ticketRequestDTO.getStatus()));

        ticketRepository.save(ticket.get());
        return ticket.get();
    }









}
