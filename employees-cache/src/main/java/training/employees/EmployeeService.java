package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public List<EmployeeDto> findAll() {
        var employees = employeeRepository.findAll();
        var targetListType = new TypeToken<List<EmployeeDto>>() {}.getType();
        return modelMapper.map(employees, targetListType);
    }

    @Cacheable("employee")
    public EmployeeDto findById(long id) {
        log.info("Find employee by id: " + id);

        return modelMapper.map(employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id))
                , EmployeeDto.class);
    }

    public EmployeeDto create(CreateEmployeeCommand command) {
        var employee = new Employee(command.getName());
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Transactional
    @CacheEvict(value = "employee", key = "#command.id")
    public EmployeeDto update(UpdateEmployeeCommand command) {
        var employee = employeeRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + command.getId()));
        employee.setName(command.getName());
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @CacheEvict("employee")
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }
}
