package shared.expenses.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Data
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
