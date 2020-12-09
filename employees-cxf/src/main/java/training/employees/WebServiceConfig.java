package training.employees;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private EmployeesEndpoint employeesEndpoint;

    @Value("${employees.endpoint.url}")
    private String endpointUrl;

    @Bean
    public Endpoint employeesEndpont() {
        var endpoint = new EndpointImpl(bus, employeesEndpoint);
        endpoint.publish("/employees");
        endpoint.setPublishedEndpointUrl(endpointUrl);
        return endpoint;
    }
}
