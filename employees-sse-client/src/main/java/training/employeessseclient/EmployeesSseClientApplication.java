package training.employeessseclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// JAX-RS

@SpringBootApplication
public class EmployeesSseClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesSseClientApplication.class, args);
	}

	private static void onEvent(InboundSseEvent inboundSseEvent) {
		System.out.println(inboundSseEvent.readData(Event.class));
	}

	private static void onError(Throwable t) {
		throw new IllegalStateException("Error processing sse", t);
	}

	private static void onComplete() {
		System.out.println("Closing...");
	}

	@Override
	public void run(String... args) throws Exception {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/api/events");
		try (SseEventSource source = SseEventSource.target(target)
				.reconnectingEvery(30, TimeUnit.SECONDS)
				.build()) {
			source.register(
					// JSON unmarshal
					EmployeesSseClientApplication::onEvent,
					EmployeesSseClientApplication::onError,
					EmployeesSseClientApplication::onComplete);
			source.open();
			new Scanner(System.in).nextLine();
		}
	}
}
