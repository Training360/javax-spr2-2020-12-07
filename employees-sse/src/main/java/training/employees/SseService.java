package training.employees;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class SseService {

    @Async
    public void publishEvents(SseEmitter emitter) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            emitter.send("Hello");

            Thread.sleep(1000);
        }
        emitter.complete();
    }
}
