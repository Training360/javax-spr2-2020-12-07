package training.employees;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateEmployeeCommand {

    @Schema(description="name of the employee", example = "John Doe")
    private String name;
}
