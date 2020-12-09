package training.employees;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(value="employee", collectionRelation="employees")
public class EmployeeDto extends RepresentationModel<EmployeeDto> {

    private Long id;

    private String name;
}
