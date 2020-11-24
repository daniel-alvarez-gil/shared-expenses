package shared.expenses.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor(staticName = "of")
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "consumer_group",
            joinColumns = {@JoinColumn(name = "consumer_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<GroupExpenses> groups;

    public Consumer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Consumer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o;
        return Objects.equals(id, consumer.id) &&
                Objects.equals(name, consumer.name) &&
                Objects.equals(groups, consumer.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, groups);
    }
}

