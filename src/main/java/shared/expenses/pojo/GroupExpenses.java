package shared.expenses.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupExpenses that = (GroupExpenses) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(consumers, that.consumers);
    }
}
