package training.employees;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class StompController {

    @MessageMapping("/messages")
    @SendTo("/topic/employees")
    public Message handleMessage(MessageCommand messageCommand) {
        return new Message("Message from server: " + messageCommand.getContent() + " " + LocalDateTime.now());
    }
}
