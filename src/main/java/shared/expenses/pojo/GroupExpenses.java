package shared.expenses.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
@Entity
public class GroupExpenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.MERGE)
    private Set<Consumer> consumers;

    public GroupExpenses() {
    }
}
