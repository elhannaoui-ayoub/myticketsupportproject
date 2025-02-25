package ayoub.elhannaoui.ticketsupport.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String category;
    private String creation_date;
    private String status;
    private int employee_id;

    private int it_support_id;

}
