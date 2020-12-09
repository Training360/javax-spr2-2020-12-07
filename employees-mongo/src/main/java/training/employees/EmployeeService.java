package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> listEmployees() {
        return employeeRepository.findAll().stream().map(e -> new EmployeeDto(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(command.getName());
        employeeRepository.save(employee);
        return new EmployeeDto(employee.getId(), employee.getName());
    }
}
