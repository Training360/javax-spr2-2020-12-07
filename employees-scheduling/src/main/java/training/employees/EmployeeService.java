package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final TaskScheduler scheduler;

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
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Employee has created: " + command.getName());
            }
        }, Duration.of(5, ChronoUnit.SECONDS));

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


    public void sayHello() {
        scheduler.scheduleWithFixedDelay(EmployeeService::run,
                Duration.of(5, ChronoUnit.SECONDS));
    }

    private static void run() {
        System.out.println("Hello Spring Boot");
    }

}
