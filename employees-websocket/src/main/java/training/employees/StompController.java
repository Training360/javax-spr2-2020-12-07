package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class StompController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/messages")
    @SendTo("/topic/employees")
    public Message handleMessage(MessageCommand messageCommand) {
        return new Message("Message from server: " + messageCommand.getContent() + " " + LocalDateTime.now());
    }

    @EventListener
    public void handleEvent(EmployeeHasCreatedEvent event) {
        template.convertAndSend("/topic/employees", new Message("Employee has created: " + event.getName()));
    }
}
