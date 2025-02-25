package ayoub.elhannaoui.ticketsupport;

import ayoub.elhannaoui.ticketsupport.entities.User;
import ayoub.elhannaoui.ticketsupport.enums.Role;
import ayoub.elhannaoui.ticketsupport.repositories.TicketRepository;
import ayoub.elhannaoui.ticketsupport.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TicketsupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsupportApplication.class, args);
    }
    /*
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository
    ){

        return args -> {
            List<User> users = userRepository.findAll();
            if(users.isEmpty()){
            User user = new User();
            //user.setId(Long.valueOf(1));
            user.setUsername("hamza");
            user.setPassword("pass");
            user.setRole(Role.EMPLOYEE);
            userRepository.save(user);

            User user2 = new User();
            //user.setId(Long.valueOf(2));
            user2.setUsername("ayoube");
            user2.setPassword("pass");
            user2.setRole(Role.EMPLOYEE);
            userRepository.save(user2);

            User user22 = new User();
            //user22.setId(Long.valueOf(22));
            user22.setUsername("nabil");
            user22.setPassword("pass");
            user22.setRole(Role.IT_SUPPORT);
            userRepository.save(user22);}
        };
    };*/
}
