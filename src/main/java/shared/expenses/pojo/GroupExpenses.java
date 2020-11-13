package shared.expenses.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class GroupExpenses {

    @Id
    private Long id;
    private String name;
}
