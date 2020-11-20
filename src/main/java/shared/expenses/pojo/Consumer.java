package shared.expenses.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
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
}

