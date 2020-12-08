package training.employees;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDto implements Serializable {

    private Long id;

    private String name;

    private int version;
}
