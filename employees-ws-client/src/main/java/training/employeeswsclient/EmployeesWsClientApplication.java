package training.employeeswsclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class EmployeesWsClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesWsClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Start");

		WebSocketClient client = new SockJsClient(
				List.of(new WebSocketTransport(new StandardWebSocketClient())));
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSessionHandler sessionHandler = new MessageStompSessionHandler();
		var future = stompClient.connect("ws://localhost:8080/websocket-endpoint", sessionHandler);
		var session = future.get(5, TimeUnit.SECONDS);
		var scanner = new Scanner(System.in);
		String line = null;
		while (!"exit".equals(line)) {
			line = scanner.nextLine();
			session.send("/app/messages", new MessageCommand(line));
		}
	}
}
