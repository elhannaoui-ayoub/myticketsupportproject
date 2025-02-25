package ayoub.elhannaoui.ticketsupport.repositories;

import ayoub.elhannaoui.ticketsupport.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {
}
