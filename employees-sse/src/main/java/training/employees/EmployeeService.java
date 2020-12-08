package training.employees;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final ApplicationEventPublisher publisher;

    public List<EmployeeDto> findAll() {
        var employees = employeeRepository.findAll();
        var targetListType = new TypeToken<List<EmployeeDto>>() {}.getType();
        return modelMapper.map(employees, targetListType);
    }

    public EmployeeDto findById(long id) {
        return modelMapper.map(employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id))
                , EmployeeDto.class);
    }

    public EmployeeDto create(CreateEmployeeCommand command) {
        var employee = new Employee(command.getName());
        employeeRepository.save(employee);
        publisher.publishEvent(new EmployeeHasCreatedEvent("Employee has been created: " + command.getName()));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Transactional
    public EmployeeDto update(UpdateEmployeeCommand command) {
        var employee = employeeRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + command.getId()));
        employee.setName(command.getName());
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }
}
