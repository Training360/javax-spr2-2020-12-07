package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class HelloController {

    // CGLIB
    private final EmployeeService employeeService;

    @GetMapping("/api/hello")
    public void sayHello() {
        log.info("Hello from controller");
        employeeService.sayHello();
    }
}
