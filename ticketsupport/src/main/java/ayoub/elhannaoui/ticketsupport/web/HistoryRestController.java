package ayoub.elhannaoui.ticketsupport.web;

import ayoub.elhannaoui.ticketsupport.entities.Comment;
import ayoub.elhannaoui.ticketsupport.entities.History;
import ayoub.elhannaoui.ticketsupport.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryRestController {
    @Autowired
    private HistoryRepository historyRepository;
    @GetMapping("/history/list")
    public List<History> findAllOrders(){
        List<History> history = historyRepository.findAll();

        return history;
    }
}
