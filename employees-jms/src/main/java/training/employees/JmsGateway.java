package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Gateway
@AllArgsConstructor
@Slf4j
public class JmsGateway {

    public static final String QUEUE_NAME = "employeesQueue";
    private final JmsTemplate jmsTemplate;

    public void sendMessage(String text) {
        jmsTemplate.convertAndSend("employeesQueue", new EmployeeHasCreatedMessage(text));
    }

    @JmsListener(destination = QUEUE_NAME)
    public void handleMessage(EmployeeHasCreatedMessage message) {
        log.info("Massage has arrived: " + message);
    }
}
