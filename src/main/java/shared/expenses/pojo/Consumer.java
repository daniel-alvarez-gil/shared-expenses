package shared.expenses.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "consumer_group",
            joinColumns = {@JoinColumn(name = "consumer_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @JsonIgnore
    private Set<GroupExpenses> groups;
}

