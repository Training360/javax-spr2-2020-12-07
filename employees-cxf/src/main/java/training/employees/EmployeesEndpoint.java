package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@WebServiceEndpoint
@WebService(serviceName = "EmployeeService", targetNamespace = "http://training360.com/employees")
@AllArgsConstructor
public class EmployeesEndpoint {

    private final EmployeeService employeeService;

    @XmlElementWrapper(name = "employees")
    @WebResult(name = "employee")
    public List<EmployeeDto> listEmployees() {
        return employeeService.findAll();
    }
}
