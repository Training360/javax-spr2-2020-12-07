package training.employees;

import lombok.Data;

@Data
public class UpdateEmployeeCommand {

    private long id;

    private String name;
}
