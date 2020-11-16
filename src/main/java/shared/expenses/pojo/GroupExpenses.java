package shared.expenses.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;


@Getter
@Setter
@Entity
public class GroupExpenses {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<Consumer> consumers;
}
