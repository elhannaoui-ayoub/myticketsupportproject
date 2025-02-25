package ayoub.elhannaoui.ticketsupport.web;

import ayoub.elhannaoui.ticketsupport.entities.Ticket;
import ayoub.elhannaoui.ticketsupport.entities.User;
import ayoub.elhannaoui.ticketsupport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/list")
    public List<User> findAllUsers(){
        List<User> users = userRepository.findAll();
        System.out.println(users);
        return users;
    }
}
