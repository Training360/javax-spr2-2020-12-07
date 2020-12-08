package training.employees;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class EmployeeHasCreatedSseController {

    private final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("/api/events")
    public SseEmitter connect() throws IOException {
        var emitter = new SseEmitter();
        emitter.send(new EmployeeHasCreatedEvent("Connected"));
        emitters.add(emitter);
        return emitter;
    }

    @EventListener
    public void publish(EmployeeHasCreatedEvent event) {
        List<SseEmitter> emittersToDelete = new ArrayList<>();
        for (SseEmitter emitter: emitters) {
            var builder =
                    SseEmitter.event()
                            .name("event")
                            .comment("Employee has created")
                            .id(UUID.randomUUID().toString())
                            .reconnectTime(10_000)
                            // JSON marshal
                            .data(event);
            try {
                emitter.send(builder);
            }
            catch (Exception e) {
                emittersToDelete.add(emitter);
            }
        }
        emitters.removeAll(emittersToDelete);
    }
}
