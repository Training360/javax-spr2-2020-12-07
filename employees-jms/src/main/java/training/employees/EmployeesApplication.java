package training.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Map;


@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public MessageConverter messageConverter(){
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTypeIdPropertyName("_typeId");
		converter.setTypeIdMappings(Map.of("empmessage", EmployeeHasCreatedMessage.class));
		return converter;
	}

	// XML message converter
//	@Bean
//	MessageConverter messageConverter() {
//		MarshallingMessageConverter converter = new MarshallingMessageConverter()
//		converter.marshaller = marshaller()
//		converter.unmarshaller = marshaller()
//		// set this converter on the implicit Spring JMS template
//		jmsTemplate.messageConverter = converter
//		converter
//	}
//
//	@Bean
//	Jaxb2Marshaller marshaller() {
//		Jaxb2Marshaller marshaller = new Jaxb2Marshaller()
//		marshaller.classesToBeBound = [My.class, MyOther.class]
//		marshaller
//	}

}
