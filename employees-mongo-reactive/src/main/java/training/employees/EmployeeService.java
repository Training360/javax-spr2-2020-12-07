package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> listEmployees() {
        return employeeRepository.findAll().map(e -> new EmployeeDto(e.getId(), e.getName()));
    }

    public Mono<EmployeeDto> createEmployee(@RequestBody Mono<CreateEmployeeCommand> command) {
        return command
                .map(c -> new Employee(c.getName()))
                .flatMap(employeeRepository::save)
                .map(e -> new EmployeeDto(e.getId(), e.getName()));
    }
}
