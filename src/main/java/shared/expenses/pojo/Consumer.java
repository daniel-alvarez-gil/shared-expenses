package shared.expenses.pojo;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.data.model.naming.NamingStrategy;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@MappedEntity(namingStrategy = NamingStrategy.class)
public class Consumer {

    @Id
    private Long id;
    private String name;
}

