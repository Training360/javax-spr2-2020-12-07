package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class SseController {

    private final SseService sseService;

    @GetMapping("/api/sse")
    public SseEmitter connect() throws IOException, InterruptedException {
        var emitter = new SseEmitter();

        sseService.publishEvents(emitter);


        return emitter;
    }
}
