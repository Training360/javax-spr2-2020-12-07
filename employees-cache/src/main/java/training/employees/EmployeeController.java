package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {

        var employee = employeeService.findById(id);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Duration.of(1, ChronoUnit.DAYS)))
                //.eTag(Integer.toString(employee.hashCode()))
                .eTag(Integer.toString(employee.getVersion()))
                .body(employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto create(@RequestBody CreateEmployeeCommand command) {
        return employeeService.create(command);
    }

    @PutMapping("{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody UpdateEmployeeCommand command) {
        command.setId(id);
        return employeeService.update(command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

}
