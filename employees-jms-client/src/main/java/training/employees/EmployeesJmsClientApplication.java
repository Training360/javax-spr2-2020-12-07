package training.employees;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Scanner;

@SpringBootApplication
public class EmployeesJmsClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesJmsClientApplication.class, args);
	}

	@JmsListener(destination = "eventsQueue")
	public void handleMessage(EmployeeHasCreatedMessage message) {
		System.out.println(message);
	}

	@Bean
	public MessageConverter messageConverter(){
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTypeIdPropertyName("_typeId");

		return converter;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Press Enter!");
		new Scanner(System.in).nextLine();
	}
}
