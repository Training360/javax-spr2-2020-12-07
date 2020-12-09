package training.employees;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public CollectionModel<EmployeeDto> findAll() {
        var employees = employeeService.findAll();
        for (var employee: employees) {
            employee.add(linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel());
        }
        var model = CollectionModel.of(employees);
        model.add(linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
        return model;
    }

    @GetMapping("{id}")
    public EmployeeDto findById(@PathVariable long id) {
        var employee = employeeService.findById(id);
        employee.add(linkTo(methodOn(EmployeeController.class).findById(id)).withSelfRel());
        return employee;
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
